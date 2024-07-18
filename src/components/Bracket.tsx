import React, { Suspense } from "react";
import {
  SingleEliminationBracket,
  Match,
} from "@g-loot/react-tournament-brackets";
import Box from "@mui/material/Box";
import Loader from "./Loader";
import { useSuspenseQuery } from "@tanstack/react-query";
import { fetchBracket } from "../queries";


export default function Matches() {
  return (
    <Suspense fallback={<Loader />}>
      <MatchesContent />
    </Suspense>
  );
}

function MatchesContent() {
  const { data } = useSuspenseQuery({
    queryKey: ["bracketData"],
    queryFn: fetchBracket,
    refetchOnWindowFocus: false
  });

  return (
    <Box
      sx={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <SingleEliminationBracket
        matches={data}
        matchComponent={Match}
      />
    </Box>
  );
}