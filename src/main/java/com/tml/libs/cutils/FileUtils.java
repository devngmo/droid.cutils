package com.tml.libs.cutils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.ContextCompat;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

//import android.support.v4.content.ContextCompat;
//import androidx.core.content.ContextCompat;

/**
 * Created by TML on 23/01/2017.
 */

@SuppressWarnings("ALL")
public class FileUtils {
    private static final String TAG = "FileUtils";

    public static String readAllText(File file) {
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            Log.e("FileUtils", "readAllText " + file.getAbsolutePath());
            e.printStackTrace();
        }
        return text.toString();
    }

    public static String readAllTextCharset(File file, Charset charset) {
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }
        return text.toString();
    }

    public static void writeAllText(Context c, String text, File f) {
        try {
            if (f.exists())
                f.delete();

            if (f.createNewFile() == false)
                return;

            FileOutputStream fos = new FileOutputStream(f);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
            //c.openFileOutput(f.getAbsolutePath(), Context.MODE_PRIVATE));
            outputStreamWriter.write(text);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * append "@appendPrefix + @text" to file
     * @param c
     * @param text
     * @param f
     * @param appendPrefix
     */
    public static void appendText(Context c, String text, File f, String appendPrefix) {
        try {
            FileOutputStream fos = new FileOutputStream(f, true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
            outputStreamWriter.append(appendPrefix);
            outputStreamWriter.append(text);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeAllBytes(Context c, byte[] data, File f) {
        try {
            if (f.exists())
                f.delete();

            if (f.createNewFile() == false)
                return;

            FileOutputStream fos = new FileOutputStream(f);
            fos.write(data);
            fos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean writeAllText(Context c, List<String> lines, String lineBreak, File f) {
        try {
            if (f.exists())
                f.delete();

            if (f.createNewFile() == false) {
                return false;
            }

            FileOutputStream fos = new FileOutputStream(f);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
            //c.openFileOutput(f.getAbsolutePath(), Context.MODE_PRIVATE));
            for (int i = 0; i < lines.size(); i++) {
                outputStreamWriter.write(lines.get(i));
                if (i < lines.size() - 1)
                    outputStreamWriter.write(lineBreak);
            }
            outputStreamWriter.close();
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void appendLines(Context c, List<String> lines, String lineBreak, File f, Boolean addLineBreakFirst) {
        try {
            FileOutputStream fos = new FileOutputStream(f, true);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            if (addLineBreakFirst)
                osw.append(lineBreak);

            for (int i = 0; i < lines.size(); i++) {
                osw.append(lines.get(i));
                if (i < lines.size() - 1)
                    osw.append(lineBreak);
            }
            osw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readStringLines(String path, boolean skipEmptyLine) {
        List<String> results = new ArrayList<>();
        BufferedReader reader;

        try{
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            while(line != null){
                line = line.trim();
                if (skipEmptyLine) {
                    if (line.length() > 0)
                        results.add(line);
                }
                else
                    results.add(line);
                line = reader.readLine();
            }

        } catch(IOException ioe){
            StaticLogger.E("FileUtils", ioe);
        }
        return  results;
    }

    public static void copyFilesInFolderAssetsToSDFolder(Context c, String assetFolderPath, File sdFolder) throws IOException {
        AssetManager am = c.getAssets();
        AssetFileDescriptor afd = null;
        String[] filenames = am.list(assetFolderPath);

        try {
            if (filenames == null) return;

            for (String fn : filenames
                 ) {
                String srcFilePath = assetFolderPath + File.separator + fn;
                Log.d(TAG, "copyFilesInFolderAssetsToSDFolder: src " + srcFilePath);
                afd = am.openFd(srcFilePath);
                if (sdFolder.exists() == false)
                    sdFolder.mkdirs();

                File dstFile = new File(sdFolder, fn);
                Log.d(TAG, "   dst " + dstFile.getAbsolutePath());
                copyFdToFile(afd.getFileDescriptor(), dstFile);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyFdToFile(FileDescriptor src, File dst) throws IOException {
        Log.d(TAG, "copyFdToFile: assetFile " + src + " dst " + dst.getAbsolutePath());
        FileChannel inChannel = new FileInputStream(src).getChannel();
        FileChannel outChannel = new FileOutputStream(dst).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }
    }

    public static boolean copyFile(File src, File dst) {
        try {
            StaticLogger.D( "copyFile: src " + src.getAbsolutePath() + " dst " + dst.getAbsolutePath());
            FileChannel inChannel = new FileInputStream(src).getChannel();
            FileChannel outChannel = new FileOutputStream(dst).getChannel();
            try {
                inChannel.transferTo(0, inChannel.size(), outChannel);

            } finally {
                if (inChannel != null)
                    inChannel.close();
                if (outChannel != null)
                    outChannel.close();
            }
            return true;
        }
        catch (Exception ex) {
            StaticLogger.E(ex);
        }
        return false;
    }

    public static boolean moveFile(File src, File dst) {
        try {
            StaticLogger.D( "moveFile: src " + src.getAbsolutePath() + " dst " + dst.getAbsolutePath());
            FileChannel inChannel = new FileInputStream(src).getChannel();
            FileChannel outChannel = new FileOutputStream(dst).getChannel();
//            try {
                inChannel.transferTo(0, inChannel.size(), outChannel);
//            } finally {
                if (inChannel != null)
                    inChannel.close();
                if (outChannel != null)
                    outChannel.close();
//            }
            src.delete();
            return true;
        }
        catch (Exception ex) {
            StaticLogger.E(ex);
        }
        return false;
    }

    public static void copyAssetFileToSD(AssetManager am, String srcFilePath, File sdFile)  {
        Log.d(TAG, "copyAssetFileToSD: src " + srcFilePath + " dst " + sdFile.getAbsolutePath());
        InputStream in = null;
        OutputStream out = null;
        String newFileName = null;
        try {
            if (sdFile.exists() == false)
                sdFile.createNewFile();
            in = am.open(srcFilePath);
            out = new FileOutputStream(sdFile);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;

        }catch (IOException ex) {
            Log.e(TAG, "copyAssetFileToSD: EXCEPTION: " + ex.getMessage());
            ex.printStackTrace();
        }
        //AssetFileDescriptor afd = null;
        //afd = am.openFd(srcFilePath);
        //if (f.exists() == false)
        //    f.createNewFile();
        //copyFdToFile(afd.getFileDescriptor(), f);
    }

    public static void deleteSDFolder(File folder) {
        String lowcaseFolder = folder.getAbsolutePath().toLowerCase();
        File sdFolder = Environment.getExternalStorageDirectory();
        if (lowcaseFolder.equals(sdFolder.getAbsolutePath().toLowerCase())) {
            Log.e(TAG, "deleteSDFolder: delete root not allowed " + sdFolder.getAbsolutePath());
            return;
        }
        if (lowcaseFolder.equals(Environment.getRootDirectory().getAbsolutePath().toLowerCase())) {
            Log.e(TAG, "deleteSDFolder: delete root folder not allowed: " +  Environment.getRootDirectory().getAbsolutePath());
            return;
        }

        if (lowcaseFolder.equals(Environment.getDataDirectory().getAbsolutePath().toLowerCase())) {
            Log.e(TAG, "deleteSDFolder: delete DATA folder not allowed: " +  Environment.getDataDirectory().getAbsolutePath());
            return;
        }

        unsafeDeleteSDFolder(folder);
    }

    private static void unsafeDeleteSDFolder(File folder) {
        Log.d(TAG, "unsafeDeleteSDFolder: " + folder.getAbsolutePath());
        File[] files = folder.listFiles();
        for (File f : files
                ) {
            if (f.isDirectory())
                unsafeDeleteSDFolder(f);
            else
                f.delete();
        }

        folder.delete();
    }

    public static List<String> readAssetTextLinesFromFile(AssetManager am, String path) {
        BufferedReader reader = null;
        List<String> lines = new ArrayList<>();
        try {
            reader = new BufferedReader(
                    new InputStreamReader(am.open(path)));

            String mLine;
            while ((mLine = reader.readLine()) != null) {
                    lines.add(mLine);
            }
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return lines;
    }
    public static String readAssetTextFromFile(AssetManager am, String path, String lineBreak) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(
                    new InputStreamReader(am.open(path)));

            String mLine;
            boolean isFirstLine = true;
            while ((mLine = reader.readLine()) != null) {
                if (isFirstLine)
                {
                    sb.append(mLine);
                    isFirstLine = false;
                }
                else
                    sb.append(lineBreak + mLine);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }


    public static void loadObjectListFromAssetFile(AssetManager am, String filePath, List<JSONObject> list) {
        Log.d(TAG, "loadObjectListFromAssetFile: " + filePath);
        String json = FileUtils.readAssetTextFromFile(am, filePath, "");
        //Log.d(TAG, "json " + json);
        JSONArray ar = null;
        try {
            ar = new JSONArray(json);

            for (int i = 0; i < ar.length(); i++) {
                try {
                    list.add(ar.getJSONObject(i));
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "    json: " + json);
        }
    }
    public static void loadObjectsFromAssetFolder(AssetManager am, String folderName, List<JSONObject> list) {
        Log.d(TAG, "loadObjectsFromAssetFolder: " + folderName);
        String json = "";
        try {
            String[] files = am.list(folderName);
            for (String file : files
                    ) {
                try {
                    json = FileUtils.readAssetTextFromFile(am, folderName + "/" + file, "\n");
                    list.add(new JSONObject(json));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "    json: " + json);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyDirectory(File sourceLocation, File targetLocation) throws IOException {
        if (targetLocation.exists() == false)
            targetLocation.mkdirs();
        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists() && !targetLocation.mkdirs()) {
                throw new IOException("Cannot create dir " + targetLocation.getAbsolutePath());
            }

            String[] children = sourceLocation.list();
            for (int i=0; i<children.length; i++) {
                copyDirectory(new File(sourceLocation, children[i]),
                        new File(targetLocation, children[i]));
            }
        } else {

            // make sure the directory we plan to store the recording in exists
            File directory = targetLocation.getParentFile();
            if (directory != null && !directory.exists() && !directory.mkdirs()) {
                throw new IOException("Cannot create dir " + directory.getAbsolutePath());
            }

            InputStream in = new FileInputStream(sourceLocation);
            OutputStream out = new FileOutputStream(targetLocation);

            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
    }

    public static void saveBitmap(Bitmap bmp, String filePath, Bitmap.CompressFormat compressFormat) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath);
            bmp.compress(compressFormat, 100, out); // bmp is your Bitmap instance
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Turn drawable resource into byte array.
     *
     * @param context parent context
     * @param id      drawable resource id
     * @return byte array
     */
    public static byte[] getFileDataFromDrawable(Context context, int id) {
        Drawable drawable = ContextCompat.getDrawable(context, id);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Turn drawable into byte array.
     *
     * @param drawable data
     * @return byte array
     */
    public static byte[] getFileDataFromDrawable(Context context, Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] readAllBytes(Context context, File f) {
        try {
            if (!f.exists())
                return null;

            FileInputStream fos = new FileInputStream(f);
            byte[] data = new byte[fos.available()];
            fos.read(data);
            fos.close();
            return data;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean saveBinaryFile(@NotNull File file, @NotNull byte[] data) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.close();
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
