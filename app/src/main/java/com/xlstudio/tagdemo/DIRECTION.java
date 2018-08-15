package com.xlstudio.tagdemo;

public enum  DIRECTION {
    LEFT,RIGHT;

    private static DIRECTION[] vals = values();

    public DIRECTION previous() {
        return vals[(this.ordinal() - 1) % vals.length];
    }

    public DIRECTION next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }
}
