package com.tml.libs.cutils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TML on 5/16/2017.
 */


public class HTMLGen {


    class HTMLNode {
        protected String tag;
        public HTMLNode(String tag) {
            this.tag = tag;

        }

        public String getChildHTML() {
            return "";
        }

        @Override
        public String toString() {
            return String.format("<%s>%s</%s>", tag, getChildHTML(), tag);
        }
    }

    class HTMLTextNode extends HTMLNode {
        protected String text;

        HTMLTextNode(String tag, String text) {
            super(tag);
            this.text = text;
        }

        @Override
        public String getChildHTML() {
            return text;
        }
    }

    class HTMLElement extends HTMLNode {
        protected List<HTMLNode> childs = new ArrayList<>();
        public HTMLElement(String tag) {
            super(tag);
        }

        @Override
        public String getChildHTML() {
            String s = "";
            for (HTMLNode n : childs
                 ) {
                s += n.toString();
            }
            return s;
        }
    }

    HTMLElement root = new HTMLElement("html");
    HTMLElement curNode;
    public HTMLGen() {
        HTMLElement body = new HTMLElement("body");
        root.childs.add(body);

        curNode = body;
    }
    public static HTMLGen New() {
        return new HTMLGen();
    }

    public HTMLGen Title(String s) {
        HTMLElement e = new HTMLElement("h1");
        e.childs.add(new HTMLTextNode("text", s));
        curNode.childs.add(e);
        return this;
    }

    @Override
    public String toString() {
        return root.toString();
    }
}
