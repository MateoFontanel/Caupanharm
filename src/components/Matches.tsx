import React, { useState, useEffect } from "react";
import {
  SingleEliminationBracket,
  Match,
  SVGViewer,
} from "@g-loot/react-tournament-brackets";
import Box from "@mui/material/Box";

interface MatchData {
  // TODO A définir
}

export default function Matches() {
  const [matches, setMatches] = useState<MatchData[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const fetchMatches = async () => {
      await new Promise((resolve) => setTimeout(resolve, 2000));
      try {
        const response = await fetch("src/assets/data/bracket.json");
        const data = await response.json();
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