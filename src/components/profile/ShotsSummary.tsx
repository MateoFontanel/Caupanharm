import React, { FC } from "react";
import { ShotsSummaryProps } from "../../interfaces/HenrikInterfaces";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";

const ShotsSummary: FC<ShotsSummaryProps> = ({ shots }) => {
  const totalShots = shots.head + shots.body + shots.leg;
  const headshotRate = Math.round((shots.head / totalShots) * 100 * 10) / 10;
  const bodyshotRate = Math.round((shots.body / totalShots) * 100 * 10) / 10;
  const legshotRate = Math.round((shots.leg / totalShots) * 100 * 10) / 10;

  const svgWidth = 88.4;
  const svgHeight = 105;

  return (
    <Box display="flex" flexDirection="row" alignItems="center" width="100%" height="100%" justifyContent="center">
      <Box height={"100%"} width={"100%"} position="relative">
        <Typography style={{position: "absolute", top: "9%", right: "0%" }}>{shots.head} tirs</Typography>
        <Typography style={{position: "absolute", top: "38%", right: "0%" }}>{shots.body} tirs</Typography>
        <Typography style={{position: "absolute", top: "70%", right: "0%" }}>{shots.leg} tirs</Typography>
      </Box>

      <div style={{ marginLeft: "8px", marginRight: "8px" }}>
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width={svgWidth * 3}
          height={svgHeight * 3}
          viewBox={`0 0 ${svgWidth} ${svgHeight}`}
        >
          <g>
            {/* Head */}
            <path
              fill="rgba(0, 0, 0)"
              d="M44.7 0Q50.7 1 51.7 5 54.7 16 51.7 22 50.7 23 48.7 23L54.7 27.4 34.7 27.4 40.7 23Q38.7 23 37.7 22 34.7 16 37.7 5 39.7 1 44.7 0"
            />
            {/* Head lines */}
            <path
              fill="rgba(0, 0, 0)"
              d="M33.2 13.95H0V13.45H33.2 M55.2 13.95H88.4V13.45H55.2V13.95"
            />
            {/* Body */}
            <path
              fill="rgba(0, 0, 0)"
              d="M54.7 28Q57.7 29 59.7 31C62.7 33 66.7 53 65.7 55Q63.7 58 62.7 57C61.7 56 56.7 38 56.7 37L55.7 51 44.7 61 33.7 51 32.7 37C32.7 38 27.7 56 26.7 57Q25.7 58 23.7 55C22.7 53 26.7 33 29.7 31Q31.7 29 34.7 28"
            />
            {/* Body lines */}
            <path
              fill="rgba(0, 0, 0)"
              d="M21.4 44.75H0V44.25H21.4V44.75 M67.6 44.75H89V44.25H67.6V44.75"
            />
            {/* Left leg */}
            <path
              fill="rgba(0, 0, 0)"
              d="M43.7 61C44.7 63 35.7 104 34.7 105Q29.7 105 29.7 100 31.7 54 33.7 52L43.7 61"
            />
            {/* Right leg */}
            <path
              fill="rgba(0, 0, 0)"
              d="M45.7 61 55.7 52Q57.7 54 59.7 100 59.7 105 54.7 105C53.7 104 44.7 63 45.7 61"
            />
            {/* Legs lines */}
            <path
              fill="rgba(0, 0, 0)"
              d="M61.6 78.75H89.4V78.28H61.6V78.75M27.8 78.75H0V78.25H27.8V78.75"
            />
          </g>
        </svg>
      </div>

      <Box height={"100%"} width={"100%"} position="relative">
        <Typography style={{position: "absolute", top: "9%", left: "0%" }}>{headshotRate}%</Typography>
        <Typography style={{position: "absolute", top: "38%", left: "0%" }}>{bodyshotRate}%</Typography>
        <Typography style={{position: "absolute", top: "70%", left: "0%" }}>{legshotRate}%</Typography>
      </Box>
    </Box>
  );
};

export default ShotsSummary;
