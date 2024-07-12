import React, { FC, useEffect, useState, useRef } from "react";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import PlayerCard from "./PlayerCard";
import MatchesAccordion from "./MatchesAccordion";
import Accordion from "@mui/material/Accordion";
import AccordionSummary from "@mui/material/AccordionSummary";
import Typography from "@mui/material/Typography";

interface AdditionalPlayerDataInterface {
  name: string;
  stats: {
    rank: string;
    favoriteAgents: string[];
    kills: number;
    deaths: number;
    assists: number;
    kast: number;
    kd: number;
    acs: number;
    killsPerRound: number;
    wins: number;
    losses: number;
  };
}

interface MatchInterface {
  date: string;
  map: string;
  result: string;
  allyScore: number;
  enemyScore: number;
}

interface PlayerMatchInterface {
  name: string;
  matches: MatchInterface[];
}

const Players: FC = () => {
  const [loading, setLoading] = useState<boolean>(true);
  const [hoveredPlayer, setHoveredPlayer] = useState<number>(-1);
  const [additionalPlayersData, setAdditionalPlayersData] = useState<AdditionalPlayerDataInterface[]>([]);
  const [playersMatchesData, setPlayersMatchesData] = useState<PlayerMatchInterface[]>([]);
  const cardsWidth = useRef<string>(`${90 / 7}vw`);

  useEffect(() => {
    const fetchPlayers = async () => {
      try {
        let response = await fetch("src/assets/data/playersMatches.json");
        let data = await response.json();
        setPlayersMatchesData(data);

        response = await fetch(
          "src/assets/data/additional_custom_players_data.json"
        );
        data = await response.json();
        setAdditionalPlayersData(data);
        cardsWidth.current = `${90 / data.length}vw`;
        
      } catch (error) {
        console.error("Error fetching data:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchPlayers();
  }, []);

  const handleCardClick = (id: number) => {
    setHoveredPlayer(id);
  };

  const renderPlayerStats = () => {
    if (hoveredPlayer === -1) {
      return <>Selectionner un joueur pour voir ses stats</>;
    } else {
      return (
        <Grid
          container
          spacing={12}
          justifyContent="center"
          alignItems="center"
        >
          <Grid item>
            <div style={{ textAlign: "center" }}>KD</div>
            <div style={{ textAlign: "center" }}>
              {additionalPlayersData[hoveredPlayer].stats.kd}
            </div>
          </Grid>
          <Grid item>
            <div style={{ textAlign: "center" }}>KAST</div>
            <div style={{ textAlign: "center" }}>
              {
                +(
                  additionalPlayersData[hoveredPlayer].stats.kast * 100
                ).toFixed(2)
              }
              %
            </div>
          </Grid>
          <Grid item>
            <div style={{ textAlign: "center" }}>ACS</div>
            <div style={{ textAlign: "center" }}>
              {additionalPlayersData[hoveredPlayer].stats.acs}
            </div>
          </Grid>
          <Grid item>
            <div style={{ textAlign: "center" }}>K/R</div>
            <div style={{ textAlign: "center" }}>
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
        <MatchesAccordion playerId={hoveredPlayer} data={playersMatchesData} />
      );
    }
  };

  if (loading) {
    return (
      <Box
        sx={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          marginTop: "128px",
        }}
      >
        <img src={"src/assets/images/loading.gif"} alt="Loading..." />
      </Box>
    );
  } else {
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
};

export default Players;
