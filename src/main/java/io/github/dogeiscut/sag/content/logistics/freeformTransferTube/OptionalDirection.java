package io.github.dogeiscut.sag.content.logistics.freeformTransferTube;

import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.Nullable;

public enum OptionalDirection implements StringRepresentable {
    NONE("none", null),
    DOWN("down", Direction.DOWN),
    UP("up", Direction.UP),
    NORTH("north", Direction.NORTH),
    SOUTH("south", Direction.SOUTH),
    WEST("west", Direction.WEST),
    EAST("east", Direction.EAST);

    private final String name;
    @Nullable
    private final Direction direction;

    OptionalDirection(String name, @Nullable Direction direction) {
        this.name = name;
        this.direction = direction;
    }

    public String getName() {
        return name;
    }

    public @Nullable Direction getDirection() {
        return direction;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public static OptionalDirection of(@Nullable Direction direction) {
        if (direction == null) {
            return NONE;
        }
        return switch (direction) {
            case DOWN -> OptionalDirection.DOWN;
            case UP -> OptionalDirection.UP;
            case NORTH -> OptionalDirection.NORTH;
            case SOUTH -> OptionalDirection.SOUTH;
            case WEST -> OptionalDirection.WEST;
            case EAST -> OptionalDirection.EAST;
        };
    }

    public @Nullable Direction from() {
        return switch (this) {
            case NONE -> null;
            case DOWN -> Direction.DOWN;
            case UP -> Direction.UP;
            case NORTH -> Direction.NORTH;
            case SOUTH -> Direction.SOUTH;
            case WEST -> Direction.WEST;
            case EAST -> Direction.EAST;
        };
    }
}
