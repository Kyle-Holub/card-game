package com.holub.kyle.player;

@SuppressWarnings("SpellCheckingInspection")
public class NpcName {
    enum FirstNames {
        ADAM,
        BOB,
        BILLY,
        CRAIG,
        DREW,
        ED,
        FRANK,
        GREG,
        HANK,
        JIM,
        KIP,
        LARRY,
        MIKE,
        NATE,
        STEW,
        TIM,
        TOM;

        @Override
        public String toString() {
            return this.name().charAt(0) + this.name().substring(1).toLowerCase();
        }
    }

    enum LastNamePrefix {
        NEURA,
        GIGA,
        POLY,
        PRIMA,
        SPECTRA,
        MICRO,
        COMPU,
        FUZZY,
        OCTAL,
        ROBO,
        TECHNI,
        TECHNO,
        NEURO,
        ALPHA,
        ELECTRO;

        @Override
        public String toString() {
            return this.name().charAt(0) + this.name().substring(1).toLowerCase();
        }
    }

    enum LastName {
        BIFFLE,
        CANKER,
        DIMICK,
        FLAPPER,
        GOBBER,
        HONKER,
        KONKER,
        LAPKIN,
        MOPPIK,
        PLUMBIS,
        RIDDLE,
        STRONK,
        THROMBUS;

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }
}
