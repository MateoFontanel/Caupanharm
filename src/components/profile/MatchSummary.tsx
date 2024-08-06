import React, { FC } from "react";
import { Grid, Paper } from "@mui/material";
import { V1LifetimeMatchItem } from "../../interfaces/HenrikInterfaces";
import Stack from "@mui/material/Stack";
import { styled } from "@mui/material/styles";
import DamageSummary from "./DamageSummary";
import ShotsSummary from "./ShotsSummary";

const ItemPrimary = styled(Paper)(({ theme }) => ({
  backgroundColor: "#fff",
  ...theme.typography.body2,
  textAlign: "center",
  color: theme.palette.text.primary,
}));

const ItemSecondary = styled(Paper)(({ theme }) => ({
  backgroundColor: "#fff",
  ...theme.typography.body2,
  textAlign: "center",
  color: theme.palette.text.secondary,
}));

const MatchSummary: FC<{ data: V1LifetimeMatchItem }> = ({ data }) => {
  return (
    <Grid container>
      <Grid container item xs={6} style={{ border: "1px dashed green" }}>
        <Grid
          container
          item
          xs={12}
          alignItems="center"
          style={{ marginBottom: 16 }}
        >
          <Grid item xs={6}>
            {data.stats.character.name}
          </Grid>
          <Grid item xs={6}>
            <Stack>
              <ItemSecondary>K/D/A</ItemSecondary>
              <ItemPrimary>
                {data.stats.kills}/{data.stats.deaths}/{data.stats.assists}
              </ItemPrimary>
            </Stack>
          </Grid>
        </Grid>
        <Grid item xs={12} alignItems="center">
          <DamageSummary damage={data.stats.damage} teams={data.teams}/>
        </Grid>
      </Grid>
      <Grid
        item
        xs={6}
        style={{ border: "1px dashed green" }}
        container
        alignItems="center"
        justifyContent="center"
      >
        <ShotsSummary shots={data.stats.shots} />
      </Grid>
    </Grid>
  );
};

export default MatchSummary;
