import React, { FC } from "react";
import { Grid, Typography, Avatar } from "@mui/material";
import { V1LifetimeMatchItem } from "../../interfaces/HenrikInterfaces";
import DamageSummary from "./DamageSummary";
import ShotsSummary from "./ShotsSummary";

const MatchSummary: FC<{ data: V1LifetimeMatchItem }> = ({ data }) => {
  return (
    <Grid container>
      <Grid container item xs={6}>
        <Grid
          container
          item
          xs={12}
          alignItems="center"
          style={{ marginBottom: 16 }}
        >
          <Grid
            container
            item
            xs={6}
            justifyContent="center"
            alignItems="center"
          >
            <Avatar
              alt={data.stats.character.name}
              variant="square"
              src={`src/assets/images/agents/${data.stats.character.name}_icon.jpg`}
              sx={{ width: "20%", height: "20%" }}
            />
          </Grid>
          <Grid item xs={6}>
            <Grid container rowSpacing={1}>
              <Grid item xs={4}>
                <Typography>Eliminations</Typography>
              </Grid>
              <Grid item xs={4}>
                <Typography>Morts</Typography>
              </Grid>
              <Grid item xs={4}>
                <Typography>Assistances</Typography>
              </Grid>
              <Grid item xs={4}>
                <Typography>{data.stats.kills}</Typography>
              </Grid>
              <Grid item xs={4}>
                <Typography>{data.stats.deaths}</Typography>
              </Grid>
              <Grid item xs={4}>
                <Typography>{data.stats.assists}</Typography>
              </Grid>
            </Grid>
          </Grid>
        </Grid>
        <Grid item xs={12} alignItems="center">
          <DamageSummary damage={data.stats.damage} teams={data.teams} />
        </Grid>
      </Grid>
      <Grid item xs={6} container alignItems="center" justifyContent="center">
        <ShotsSummary shots={data.stats.shots} />
      </Grid>
    </Grid>
  );
};

export default MatchSummary;
