import * as React from "react";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Typography from "@mui/material/Typography";
import CardActionArea from "@mui/material/CardActionArea";
import PropTypes from "prop-types";
import { styled } from "@mui/material/styles";
import Avatar from '@mui/material/Avatar';
import Stack from '@mui/material/Stack';

import * as Utils from "../utils.js";

const UsernameTypography = styled(Typography)(({ theme }) => ({
  color: theme.palette.text.main,
}));

const UsertagTypography = styled(Typography)(({ theme }) => ({
  color: theme.palette.text.disabled,
  marginLeft: theme.spacing(0.25),
}));

export default function PlayerCard({ id, onClick, width, player }) {
  const handleCardClick = () => {
    if (onClick) {
      onClick(id);
    }
  };

  return (
    <Card sx={{ maxWidth: width }}>
      <CardActionArea onClick={handleCardClick}>
        <CardMedia component="img" height="140" image={Utils.getAgentCardWide(player.stats.favoriteAgents[0])} alt={player.stats.favoriteAgents[0]}/>
        <CardContent>
          <Typography gutterBottom variant="h5" component="div">
            <UsernameTypography component="span">
              {Utils.getUsername(player.name)}
            </UsernameTypography>
            <UsertagTypography component="span">
              #{Utils.getUsertag(player.name)}
            </UsertagTypography>
          </Typography>
          <Typography variant="body2" color="text.secondary">
            {Utils.formatPlayerGamesStats(player.stats.wins,player.stats.losses)}
          </Typography>
          <Stack spacing={1} direction="row" marginTop="4px" justifyContent="center" alignItems="center">
            {player.stats.favoriteAgents.map((agent, index) => (
              <Avatar alt={agent} key={index} variant="rounded" src={`src/assets/images/agents/${agent}_icon.jpg`}></Avatar>
            ))}
          </Stack>
        </CardContent>
      </CardActionArea>
    </Card>
  );
}

PlayerCard.propTypes = {
  id: PropTypes.number,
  onClick: PropTypes.func,
  width: PropTypes.string,
  player: PropTypes.object.isRequired,
};
