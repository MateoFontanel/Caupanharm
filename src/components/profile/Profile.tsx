import React, { FC, Suspense, useState } from "react";
import { ProfileProps } from "../../interfaces/Interfaces";
import {
  V1LifetimeMatches,
  HenrikErrorsInterface,
} from "../../interfaces/HenrikInterfaces";
import Loader from "../Loader";
import {
  useSuspenseQuery,
  QueryClient,
  QueryClientProvider,
} from "@tanstack/react-query";
import { fetchPlayerMatches } from "../../queries";
import FullMatchAccordion from "./FullMatchesAccordion";
import Box from "@mui/material/Box";
import NavButtons from "../NavButtons";

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      retry: 0,
    },
  },
});

const Profile: FC<ProfileProps> = ({ username }) => {
  return (
    <QueryClientProvider client={queryClient}>
      <Suspense fallback={<Loader />}>
        <ProfileContent username={username}></ProfileContent>
      </Suspense>
    </QueryClientProvider>
  );
};

const ProfileContent: FC<ProfileProps> = ({ username }) => {
  const { data: playerMatchesData } = useSuspenseQuery({
    queryKey: ["playerMatchesData", username],
    queryFn: () => fetchPlayerMatches(username),
    refetchOnWindowFocus: false,
  });

  return (
    <div style={{ textAlign: "center", fontFamily: "Roboto, sans-serif" }}>
      {defineComponent(username, playerMatchesData)}
    </div>
  );
};

function defineComponent(
  username: string,
  data: V1LifetimeMatches | HenrikErrorsInterface
) {
  const [matchPage, setMatchPage] = useState<number>(0);

  if (isV1LifetimeMatchesInterface(data)) {
    return (
      <Box sx={{ width: "100%" }}>
        <NavButtons page={matchPage} setPage={setMatchPage} dataSize={data.results.returned} pageSize={15}/>
        <FullMatchAccordion player={username} data={data} page={matchPage} />
      </Box>
    );
  } else {
    return "error";
  }
}

function isV1LifetimeMatchesInterface(
  data: V1LifetimeMatches | HenrikErrorsInterface
): data is V1LifetimeMatches {
  return "results" in data;
}

export default Profile;
