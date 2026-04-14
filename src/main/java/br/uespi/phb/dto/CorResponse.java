package br.uespi.phb.dto;

public record CorResponse(
                Rgb rgb,
                Cmyk cmyk,
                Hsv hsv,
                Hsl hsl,
                String hex) {

        public CorResponse(Rgb rgb, Cmyk cmyk, Hsv hsv, Hsl hsl) {
                this(rgb, cmyk, hsv, hsl,
                                String.format("#%02X%02X%02X", rgb.r(), rgb.g(), rgb.b()));
        }
}
