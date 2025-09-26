import {ReactElement} from "react";
import s from "./ProcStats.module.scss";

export function thingsFromNum(val: number): ReactElement {
    val = Math.max(0.1, val)

    const colorClass = val < 25 ? s.green :
        val < 50 ? s.yellow :
            val < 75 ? s.orange :
                s.red

    return <span className={colorClass}>{val}%</span>

    return <>[<span className={colorClass}>{"#".repeat(val/4)}</span><span className={s.dark}>{"#".repeat(100/4-val/4)}</span>]</>
}