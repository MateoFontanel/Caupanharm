import React, { FC } from "react";
import { DamageSummaryProps } from "../../interfaces/HenrikInterfaces";
import GaugeChart from "react-gauge-chart";
import Box from "@mui/material/Box";

const DamageSummary: FC<DamageSummaryProps> = ({ damage, teams }) => {
  const damageMade = damage.made;
  const damageReceived = damage.received;
  const roundsPlayed = teams.blue! + teams.red!;
  const damageDelta = Math.round(((damageMade - damageReceived) / roundsPlayed) * 10) / 10;
  const gaugePercent = scaleDelta(damageDelta)

  return (
    <Box display="flex" flexDirection="column" alignItems="center" width="100%" height="100%" justifyContent="center">
      Diff dégâts : {damageDelta}
      <GaugeChart id="gauge-chart-damage" style={{width:"50%"}}
      percent={gaugePercent}
      nrOfLevels={2}
      colors={["#FF0000","#00FF00"]}
      arcWidth={0.125}
      arcPadding={0.01}
      cornerRadius={3} 
      animate={false} 
      needleColor={"#000000"}
      hideText={true}
      needleScale={0.8}
      />
    </Box>
  );
};

function scaleDelta(delta: number){
    if(delta < -150){
        return 0
    }else if(delta > 150){
        return 1
    }else{
        return (delta + 150) / 300
    }
}

export default DamageSummary;
