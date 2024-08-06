import React, { FC } from "react";
import { DamageSummaryProps} from "../../interfaces/HenrikInterfaces";

const DamageSummary: FC<DamageSummaryProps> = ({damage, teams}) => {
    const damageMade = damage.made
    const damageReceived = damage.received
    const roundsPlayed = teams.blue! + teams.red!



    return <>Diff dégâts : {Math.round(((damageMade - damageReceived) / roundsPlayed)*10)/10}</>

};


export default DamageSummary