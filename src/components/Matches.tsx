import React, { useState, useEffect } from "react";
import {
  SingleEliminationBracket,
  Match
} from "@g-loot/react-tournament-brackets";
import Box from "@mui/material/Box";

interface MatchParticipantInterface {
  id: string,
  resultText: string,
  isWinner: boolean,
  status: 'PLAYED' | 'NO_SHOW' | 'WALK_OVER' | 'NO_PARTY' | null,
  name: string
}

interface MatchInterface {
  id: string,
  nextMatchId: string,
  tournamentRoundText: string,
  state: 'NO_SHOW' | 'WALK_OVER' | 'NO_PARTY' | 'DONE' | 'SCORE_DONE',
  participants: MatchParticipantInterface[]
}

export default function Matches() {
  const [matches, setMatches] = useState<MatchInterface[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const fetchMatches = async () => {
      try {
        const response = await fetch("src/assets/data/bracket.json");
        const data: MatchInterface[] = await response.json();
        setMatches(data);
      } catch (error) {
        console.error("Error fetching data:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchMatches();
  }, []);

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
      <Box
        sx={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <SingleEliminationBracket
          matches={matches}
          matchComponent={Match}
        ></SingleEliminationBracket>
      </Box>
    );
  }
}