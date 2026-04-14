package br.uespi.phb.service;

import br.uespi.phb.dto.Cmyk;
import br.uespi.phb.dto.CorResponse;
import br.uespi.phb.dto.Hsl;
import br.uespi.phb.dto.Hsv;
import br.uespi.phb.dto.Rgb;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConversorService {

    public CorResponse converterCoresAPartirDeRgb(Rgb rgb) {
        Cmyk cmyk = rgbParaCmyk(rgb);
        Hsv hsv = rgbParaHsv(rgb);
        Hsl hsl = rgpParaHsl(rgb);
        return new CorResponse(rgb, cmyk, hsv, hsl);
    }

    public CorResponse converterCoresAPartirDeCmyk(Cmyk cmyk) {
        Rgb rgb = cmykParaRgb(cmyk);
        Hsv hsv = rgbParaHsv(rgb);
        Hsl hsl = rgpParaHsl(rgb);

        return new CorResponse(rgb, cmyk, hsv, hsl);
    }

    public CorResponse converterCoresAPartirDeHsl(Hsl hsl) {
        Rgb rgb = hslParaRgb(hsl);
        return converterCoresAPartirDeRgb(rgb);
    }

    public CorResponse converterCoresAPartirDeHsv(Hsv hsv) {
        Rgb rgb = hsvParaRgb(hsv);
        return converterCoresAPartirDeRgb(rgb);
    }

    private Cmyk rgbParaCmyk(Rgb rgb) {
        double rNormalizado = rgb.r() / 255.0;
        double gNormalizado = rgb.g() / 255.0;
        double bNormalizado = rgb.b() / 255.0;

        double k = 1.0 - Math.max(Math.max(rNormalizado, gNormalizado), bNormalizado);
        if (k == 1.0)
            return new Cmyk(0, 0, 0, 1);

        double c = (1.0 - rNormalizado - k) / (1.0 - k);
        double m = (1.0 - gNormalizado - k) / (1.0 - k);
        double y = (1.0 - bNormalizado - k) / (1.0 - k);
        return new Cmyk(
                round(c),
                round(m),
                round(y),
                round(k));
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private Hsv rgbParaHsv(Rgb rgb) {
        double rNormalizado = rgb.r() / 255.0;
        double gNormalizado = rgb.g() / 255.0;
        double bNormalizado = rgb.b() / 255.0;

        double corMaior = Double.max(rNormalizado, Double.max(gNormalizado, bNormalizado));
        double corMenor = Double.min(rNormalizado, Double.min(gNormalizado, bNormalizado));
        double delta = corMaior - corMenor;

        double h = 0;

        if (delta == 0) {
            h = 0;
        } else if (corMaior == rNormalizado) {
            h = 60 * (((gNormalizado - bNormalizado) / delta) % 6);
        } else if (corMaior == gNormalizado) {
            h = 60 * (((bNormalizado - rNormalizado) / delta) + 2);
        } else if (corMaior == bNormalizado) {
            h = 60 * (((rNormalizado - gNormalizado) / delta) + 4);
        }

        if (h < 0) {
            h += 360;
        }

        double s;
        if (corMaior == 0) {
            s = 0;
        } else {
            s = delta / corMaior;
        }

        double v = corMaior;

        h = Math.round(h * 100.0) / 100.0;
        s = Math.round(s * 100.0) / 100.0;
        v = Math.round(v * 100.0) / 100.0;

        return new Hsv(h, s, v);

    }

    private Hsl rgpParaHsl(Rgb rgb) {
        double rNormalizado = rgb.r() / 255.0;
        double gNormalizado = rgb.g() / 255.0;
        double bNormalizado = rgb.b() / 255.0;

        double corMaior = Double.max(rNormalizado, Double.max(gNormalizado, bNormalizado));
        double corMenor = Double.min(rNormalizado, Double.min(gNormalizado, bNormalizado));
        double delta = corMaior - corMenor;

        double h = 0;

        if (delta == 0) {
            h = 0;
        } else if (corMaior == rNormalizado) {
            h = 60 * (((gNormalizado - bNormalizado) / delta) % 6);
        } else if (corMaior == gNormalizado) {
            h = 60 * (((bNormalizado - rNormalizado) / delta) + 2);
        } else if (corMaior == bNormalizado) {
            h = 60 * (((rNormalizado - gNormalizado) / delta) + 4);
        }

        if (h < 0) {
            h += 360;
        }

        double l = (corMaior + corMenor) / 2;

        double s;
        if (delta == 0) {
            s = 0;
        } else {
            s = delta / (1 - Math.abs(2 * l - 1));
        }

        h = Math.round(h * 100.0) / 100.0;
        s = Math.round(s * 100.0) / 100.0;
        l = Math.round(l * 100.0) / 100.0;

        return new Hsl(h, s, l);

    }

    private Rgb cmykParaRgb(Cmyk cmyk) {
        int r = (int) (255 * (1 - cmyk.c()) * (1 - cmyk.k()));
        int g = (int) (255 * (1 - cmyk.m()) * (1 - cmyk.k()));
        int b = (int) (255 * (1 - cmyk.y()) * (1 - cmyk.k()));
        return new Rgb(r, g, b);
    }

    private Rgb hsvParaRgb(Hsv hsv) {
        double croma = hsv.v() * hsv.s();
        double x = croma * (1 - Math.abs((hsv.h() / 60.0) % 2 - 1));
        double m = hsv.v() - croma;

        double rLinha = 0;
        double gLinha = 0;
        double bLinha = 0;

        if (hsv.h() >= 0 && hsv.h() < 60) {
            rLinha = croma;
            gLinha = x;
            bLinha = 0;
        } else if (hsv.h() >= 60 && hsv.h() < 120) {
            rLinha = x;
            gLinha = croma;
            bLinha = 0;
        } else if (hsv.h() >= 120 && hsv.h() < 180) {
            rLinha = 0;
            gLinha = croma;
            bLinha = x;
        } else if (hsv.h() >= 180 && hsv.h() < 240) {
            rLinha = 0;
            gLinha = x;
            bLinha = croma;
        } else if (hsv.h() >= 240 && hsv.h() < 300) {
            rLinha = x;
            gLinha = 0;
            bLinha = croma;
        } else {
            rLinha = croma;
            gLinha = 0;
            bLinha = x;
        }

        int r = (int) Math.round((rLinha + m) * 255);
        int g = (int) Math.round((gLinha + m) * 255);
        int b = (int) Math.round((bLinha + m) * 255);

        return new Rgb(r, g, b);
    }

    private Rgb hslParaRgb(Hsl hsl) {
        double c = (1 - Math.abs(2 * hsl.l() - 1)) * hsl.s();
        double x = c * (1 - Math.abs((hsl.h() / 60.0) % 2 - 1));
        double m = hsl.l() - c / 2.0;

        double rLinha = 0;
        double gLinha = 0;
        double bLinha = 0;

        if (hsl.h() < 60) {
            rLinha = c;
            gLinha = x;
            bLinha = 0;
        } else if (hsl.h() < 120) {
            rLinha = x;
            gLinha = c;
            bLinha = 0;
        } else if (hsl.h() < 180) {
            rLinha = 0;
            gLinha = c;
            bLinha = x;
        } else if (hsl.h() < 240) {
            rLinha = 0;
            gLinha = x;
            bLinha = c;
        } else if (hsl.h() < 300) {
            rLinha = x;
            gLinha = 0;
            bLinha = c;
        } else {
            rLinha = c;
            gLinha = 0;
            bLinha = x;
        }

        int r = (int) ((rLinha + m) * 255);
        int g = (int) ((gLinha + m) * 255);
        int b = (int) ((bLinha + m) * 255);

        return new Rgb(r, g, b);
    }

}
