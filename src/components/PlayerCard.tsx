import React, { FC } from "react";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Typography, { TypographyProps } from "@mui/material/Typography";
import CardActionArea from "@mui/material/CardActionArea";
import { styled } from "@mui/material/styles";
import Avatar from "@mui/material/Avatar";
import Stack from "@mui/material/Stack";
import * as Utils from "../utils";
import {PlayerCardProps} from "../interfaces"



interface UserTypographyProps extends TypographyProps {
  component?: React.ElementType; // Permet à styled de prendre en charge la prop 'component'
}

const UsernameTypography = styled((props: UserTypographyProps) => (
  <Typography {...props} />
))(({ theme }) => ({
  color: theme.palette.text.primary,
}));

const UsertagTypography = styled((props: UserTypographyProps) => (
  <Typography {...props} />
))(({ theme }) => ({
  color: theme.palette.text.disabled,
  marginLeft: theme.spacing(0.25),
}));


const PlayerCard: FC<PlayerCardProps> = ({ id, onClick, width, player }) => {
  const handleCardClick = () => {
    if (onClick) {
      onClick(id);
    }
  };

  return (
    <Card sx={{ maxWidth: width }}>
      <CardActionArea onClick={handleCardClick}>
        <CardMedia
          component="img"
          height="140"
          image={Utils.getAgentCardWide(player.stats.favoriteAgents[0])}
          alt={player.stats.favoriteAgents[0]}
        />
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
            {Utils.formatPlayerGamesStats(
              player.stats.wins,
              player.stats.losses
            )}
          </Typography>
          <Stack
            spacing={1}
            direction="row"
            marginTop="4px"
            justifyContent="center"
            alignItems="center"
          >
            {player.stats.favoriteAgents.map((agent, index) => (
              <Avatar
                alt={agent}
                key={index}
                variant="rounded"
                src={`src/assets/images/agents/${agent}_icon.jpg`}
              ></Avatar>
            ))}
          </Stack>
        </CardContent>
      </CardActionArea>
    </Card>
  );
};

export default PlayerCard;
