import s from "./NewsTable.module.scss";
import {ReactElement} from "react";

export function starsFromNum(val: number): ReactElement {
    const safeVal = Math.round(val); // Zaokrąglij do najbliższej liczby całkowitej
    const clamped = Math.max(0, Math.min(10, safeVal)); // Ogranicz do zakresu 0–10
    // return "★".repeat(clamped) + "☆".repeat(10 - clamped);
    return <><span>{"★".repeat(clamped)}</span><span className={s.starsDark}>{"☆".repeat(10-clamped)}</span></>
}