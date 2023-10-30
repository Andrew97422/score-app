export enum FontFamily {
    Roboto,
    Arial,
    Helvetica,
    Verdana,
    TrebuchetMS,
    GillSans,
    NotoSans,
    Optima,
    ArialNarrow
}

export class FontFamilyExt {
    static getName(v: FontFamily): string {
        switch(v) {
            case FontFamily.Roboto:
                return 'Roboto, sans-serif'
            case FontFamily.Arial:
                return 'Arial, sans-serif'
            case FontFamily.Helvetica:
                return 'Helvetica, sans-serif'
            case FontFamily.Verdana:
                return 'Verdana, sans-serif'
            case FontFamily.TrebuchetMS:
                return 'Trebuchet MS, sans-serif'
            case FontFamily.GillSans:
                return 'Gill Sans, sans-serif'
            case FontFamily.NotoSans:
                return 'Noto Sans, sans-serif'
            case FontFamily.Optima:
                return 'Optima, sans-serif'
            case FontFamily.ArialNarrow:
                return 'Arial Narrow, sans-serif'
            default:
                return ''
        }
    }

    static getAll(): string[] {
        return [
            'Roboto, sans-serif',
            'Arial, sans-serif',
            'Helvetica, sans-serif',
            'Verdana, sans-serif',
            'Trebuchet MS, sans-serif',
            'Gill Sans, sans-serif',
            'Noto Sans, sans-serif',
            'Optima, sans-serif',
            'Arial Narrow, sans-serif'
        ]
    }
}
