export enum WidgetTheme {
    Standard,
    Blue,
    Orange,
    Gray
}

export class WidgetThemeExt {
    static getName(v: WidgetTheme): string {
        switch(v) {
            case WidgetTheme.Standard:
                return 'Стандартная'
            case WidgetTheme.Blue:
                return 'Синяя'
            case WidgetTheme.Orange:
                return 'Оранжевая'
            case WidgetTheme.Gray:
                return 'Серая'
            default:
                return ''
        }
    }

    static getClassName(v: WidgetTheme): string {
        switch(v) {
            case WidgetTheme.Standard:
                return 'standart-widget-theme'
            case WidgetTheme.Blue:
                return 'blue-widget-theme'
            case WidgetTheme.Orange:
                return 'orange-widget-theme'
            case WidgetTheme.Gray:
                return 'gray-widget-theme'
            default:
                return ''
        }
    }

    static getAll(): WidgetTheme[] {
        return Object
        .keys(WidgetTheme)
        .map(x => +x as unknown as number)
        .filter(x => !Number.isNaN(x));
    }
}