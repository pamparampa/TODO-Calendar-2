package com.example.radle.todo_calendar2.calendarView.scrolling;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class ScrollEffectParameters {


    private final int animationDuration;
    private final Side side;
    private ElementsToChangeAfterScroll elementsToChangeAfterScroll;

    public ScrollEffectParameters(final int animationDuration, final Side side) {
        this.animationDuration = animationDuration;
        this.side = side;
    }

    public ScrollEffectParameters(final int animationDuration, final Side side,
                                  final ElementsToChangeAfterScroll elementsToChangeAfterScroll) {
        this(animationDuration, side);
        this.elementsToChangeAfterScroll = elementsToChangeAfterScroll;
    }

    public Side getSide() {
        return this.side;
    }

    public int getAnimationDuration() {
        return this.animationDuration;
    }

    public Optional<ElementsToChangeAfterScroll> getElementsToChangeAfrerScroll() {
        if (this.elementsToChangeAfterScroll == null) {
            return Optional.empty();
        } else return Optional.of(this.elementsToChangeAfterScroll);
    }

    public enum Side {
        LEFT, SAME, RIGHT

    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ScrollEffectParameters)) return false;
        final ScrollEffectParameters that = (ScrollEffectParameters) o;
        return this.animationDuration == that.animationDuration &&
                this.side == that.side &&
                Objects.equals(this.elementsToChangeAfterScroll, that.elementsToChangeAfterScroll);
    }

    @Override
    public String toString() {
        return "ScrollEffectParameters{" +
                "animationDuration=" + this.animationDuration +
                ", side=" + this.side +
                ", elementsToChangeAfterScroll=" + this.elementsToChangeAfterScroll +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.animationDuration, this.side, this.elementsToChangeAfterScroll);
    }

    public static class ElementsToChangeAfterScroll {
        LocalDateTime newElementDateTime;
        int elementToRemoveId;

        public ElementsToChangeAfterScroll(final LocalDateTime newElementDateTime,
                                           final int elementToRemoveId) {
            this.newElementDateTime = newElementDateTime;
            this.elementToRemoveId = elementToRemoveId;
        }

        public LocalDateTime getNewElementDateTime() {
            return this.newElementDateTime;
        }

        public int getElementToRemoveId() {
            return this.elementToRemoveId;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (!(o instanceof ElementsToChangeAfterScroll)) return false;
            final ElementsToChangeAfterScroll that = (ElementsToChangeAfterScroll) o;
            return this.elementToRemoveId == that.elementToRemoveId &&
                    Objects.equals(this.newElementDateTime, that.newElementDateTime);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.newElementDateTime, this.elementToRemoveId);
        }

        @Override
        public String toString() {
            return "ElementsToChangeAfterScroll{" +
                    "newElementDateTime=" + this.newElementDateTime +
                    ", elementToRemoveId=" + this.elementToRemoveId +
                    '}';
        }
    }
}
