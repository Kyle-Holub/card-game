package com.holub.kyle.game.player;

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
        BONK,
        CRUG,
        DING,
        FRING,
        GOBBER,
        HONKER,
        KONKER,
        MUPS,
        PLUMBUS,
        ROGNAR,
        STRONK,
        THROMBUS;

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }
}
