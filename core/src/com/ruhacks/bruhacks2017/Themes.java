package com.ruhacks.bruhacks2017;

public enum Themes {

    BLUE_DAY,
    BLUE_NIGHT,
    PURPLE_NIGHT,
    PINK_MORNING,
    BROWN_EVENING;

    public float[][] getColors() {
        switch(this) {
            case BLUE_DAY:
                return new float[][]{{88f / 255f, 141f / 255f, 125f / 255f}, {102f / 255f, 154f / 255f, 141f / 255f}, {138f / 255f, 181f / 255f, 163f / 255f}};
            case BLUE_NIGHT:
                return new float[][]{{43f / 255f, 59f / 255f, 74f / 255f}, {60f / 255f, 76f / 255f, 92f / 255f}, {69f / 255f, 87f / 255f, 109f / 255f}};
        }
        return new float[][]{{88f / 255f, 141f / 255f, 125f / 255f}, {102f / 255f, 154f / 255f, 141f / 255f}, {138f / 255f, 181f / 255f, 163f / 255f}};
    }

    public float[] getBackgroundColor() {
        switch (this) {
            case BLUE_DAY:
                return new float[]{182f / 255f, 213f / 255f, 187f / 255f};
            case BLUE_NIGHT:
                return new float[]{72f / 255f, 94f / 255f, 115f / 255f};
        }
        return new float[]{182f / 255f, 213f / 255f, 187f / 255f};
    }
}
