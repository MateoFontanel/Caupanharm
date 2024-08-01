import React, { useState, useRef, Suspense } from "react";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import PlayerCard from "./PlayerCard";
import DemoMatchesAccordion from "./DemoMatchesAccordion";
import Accordion from "@mui/material/Accordion";
import AccordionSummary from "@mui/material/AccordionSummary";
import Typography from "@mui/material/Typography";
import { fetchPlayers, fetchStats } from "../queries";
import { useSuspenseQuery } from "@tanstack/react-query";
import Loader from "./Loader";

export default function Players() {
  return (
    <Suspense fallback={<Loader />}>
      <PlayersContent />
    </Suspense>
  );
}

function PlayersContent() {
  const { data: playersMatchesData } = useSuspenseQuery({
    queryKey: ["playersMatchesData"],
    queryFn: fetchPlayers,
    refetchOnWindowFocus: false,
  });

  const { data: additionalPlayersData } = useSuspenseQuery({
    queryKey: ["additionalPlayersData"],
    queryFn: fetchStats,
    refetchOnWindowFocus: false,
  });

  const [hoveredPlayer, setHoveredPlayer] = useState<number>(-1);
  const cardsWidth = useRef<string>(`${98 / 7}vw`);

  const handleCardClick = (id: number) => {
    setHoveredPlayer(id);
  };

  const renderPlayerStats = () => {
    if (hoveredPlayer === -1) {
      return (
        <div style={{ fontFamily: "Roboto, sans-serif" }}>
          Selectionner un joueur pour voir ses stats
        </div>
      );
    } else {
      return (
        <Grid
          container
          spacing={12}
          justifyContent="center"
          alignItems="center"
        >
          <Grid item>
            <div
              style={{ textAlign: "center", fontFamily: "Roboto, sans-serif" }}
            >
              KD
            </div>
            <div
              style={{ textAlign: "center", fontFamily: "Roboto, sans-serif" }}
            >
              {additionalPlayersData[hoveredPlayer].stats.kd}
            </div>
          </Grid>
          <Grid item>
            <div
              style={{ textAlign: "center", fontFamily: "Roboto, sans-serif" }}
            >
              KAST
            </div>
            <div
              style={{ textAlign: "center", fontFamily: "Roboto, sans-serif" }}
            >
              {
                +(
                  additionalPlayersData[hoveredPlayer].stats.kast * 100
                ).toFixed(2)
              }
              %
            </div>
          </Grid>
          <Grid item>
            <div
              style={{ textAlign: "center", fontFamily: "Roboto, sans-serif" }}
            >
              ACS
            </div>
            <div
              style={{ textAlign: "center", fontFamily: "Roboto, sans-serif" }}
            >
              {additionalPlayersData[hoveredPlayer].stats.acs}
            </div>
          </Grid>
          <Grid item>
            <div
              style={{ textAlign: "center", fontFamily: "Roboto, sans-serif" }}
            >
              K/R
            </div>
            <div
              style={{ textAlign: "center", fontFamily: "Roboto, sans-serif" }}
            >
              {additionalPlayersData[hoveredPlayer].stats.killsPerRound}
            </div>
          </Grid>
        </Grid>
      );
    }
  };

  const renderPlayerMatches = () => {
    if (hoveredPlayer === -1) {
      return (
        <Accordion disabled>
          <AccordionSummary aria-controls="disabled-panel" id="disabled-panel">
            <Typography>
              Selectionner un joueur pour voir son historique de matchs
            </Typography>
          </AccordionSummary>
        </Accordion>
      );
    } else {
      return (
        <DemoMatchesAccordion playerId={hoveredPlayer} data={playersMatchesData} />
      );
    }
  };

  return (
    <>
      <Box
        sx={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          p: 2,
          margin: "1vw",
        }}
      >
        <Grid
          container
          spacing={1}
          justifyContent="center"
          alignItems="center"
        >
          {additionalPlayersData.map((data, index) => (
            <Grid item key={index}>
              <Box
                sx={{
                  display: "flex",
                  justifyContent: "center",
                  alignItems: "center",
                }}
              >
                <PlayerCard
                  id={index}
                  onClick={handleCardClick}
                  width={cardsWidth.current}
                  player={data}
                ></PlayerCard>
              </Box>
            </Grid>
          ))}
        </Grid>
      </Box>
      <Box
        sx={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          p: 2,
          margin: "1vw",
        }}
      >
        {renderPlayerStats()}
      </Box>
      <Box
        sx={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          p: 2,
          margin: "1vw",
        }}
      >
        {renderPlayerMatches()}
      </Box>
    </>
  );
}