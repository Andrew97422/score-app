export enum WorkExperience {
    LESS_THAN_YEAR_AND_HALF,
    ONE_AND_HALF_TO_TEN,
    ELEVEN_TO_TWENTY,
    MORE_THAN_TWENTY
}

export class WorkExperienceExt {
    static getName(v: WorkExperience): string {
        switch(v) {
            case WorkExperience.LESS_THAN_YEAR_AND_HALF:
                return 'Меньше 1.5 лет'
            case WorkExperience.ONE_AND_HALF_TO_TEN:
                return 'От 1.5 до 10 лет'
            case WorkExperience.ELEVEN_TO_TWENTY:
                return 'От 11 до 20 лет'
            case WorkExperience.MORE_THAN_TWENTY:
                return 'Больше 20 лет'
            default:
                return ''
        }
    }

    static getAll(): WorkExperience[] {
        return Object
        .keys(WorkExperience)
        .map(x => +x as WorkExperience)
        .filter(x => !Number.isNaN(x) && x >= 0)
    }
}
