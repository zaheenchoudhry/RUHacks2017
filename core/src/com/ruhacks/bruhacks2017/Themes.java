package com.ruhacks.bruhacks2017;

public enum Themes {

    BLUE_DAY,
    BLUE_NIGHT,
    PURPLE_NIGHT,
    PINK_MORNING,
    BROWN_EVENING,
    PURPLE_DAY,
    RED;

    public float[][] getColors() {
        switch(this) {
            case BLUE_DAY:
                return new float[][]{{88f / 255f, 141f / 255f, 125f / 255f}, {102f / 255f, 154f / 255f, 141f / 255f}, {138f / 255f, 181f / 255f, 163f / 255f}};
            case BLUE_NIGHT:
                return new float[][]{{43f / 255f, 59f / 255f, 74f / 255f}, {60f / 255f, 76f / 255f, 92f / 255f}, {69f / 255f, 87f / 255f, 109f / 255f}};
            case PINK_MORNING:
                return new float[][]{{109f / 255f, 79f / 255f, 74f / 255f}, {127f / 255f, 90f / 255f, 82f / 255f}, {168f / 255f, 116f / 255f, 102f / 255f}};
            case BROWN_EVENING:
                return new float[][]{{121f / 255f, 93f / 255f, 71f / 255f}, {130f / 255f, 100f / 255f, 76f / 255f}, {164f / 255f, 121f / 255f, 88f / 255f}};
            case PURPLE_NIGHT:
                return new float[][]{{80f / 255f, 72f / 255f, 93f / 255f}, {89f / 255f, 80f / 255f, 99f / 255f}, {120f / 255f, 110f / 255f, 134f / 255f}};
            case PURPLE_DAY:
                return new float[][]{{70f / 255f, 54f / 255f, 101f / 255f}, {85f / 255f, 68f / 255f, 121f / 255f}, {153f / 255f, 104f / 255f, 149f / 255f}};
            case RED:
                return new float[][]{{99f / 255f, 1f / 255f, 0f / 255f}, {123f / 255f, 31f / 255f, 21f / 255f}, {171f / 255f, 60f / 255f, 40f / 255f}};
        }
        return new float[][]{{88f / 255f, 141f / 255f, 125f / 255f}, {102f / 255f, 154f / 255f, 141f / 255f}, {138f / 255f, 181f / 255f, 163f / 255f}};
    }

    public float[] getBackgroundColor() {
        switch (this) {
            case BLUE_DAY:
                return new float[]{182f / 255f, 213f / 255f, 187f / 255f};
            case BLUE_NIGHT:
                return new float[]{72f / 255f, 94f / 255f, 115f / 255f};
            case PINK_MORNING:
                return new float[]{192f / 255f, 131f / 255f, 110f / 255f};
            case BROWN_EVENING:
                return new float[]{181f / 255f, 137f / 255f, 98f / 255f};
            case PURPLE_NIGHT:
                return new float[]{135f / 255f, 117f / 255f, 134f / 255f};
            case PURPLE_DAY:
                return new float[]{188f / 255f, 134f / 255f, 168f / 255f};
            case RED:
                return new float[]{172f / 255f, 73f / 255f, 52f / 255f};
        }
        return new float[]{182f / 255f, 213f / 255f, 187f / 255f};
    }
}
