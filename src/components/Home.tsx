import React, {useState} from "react";
import Box from "@mui/material/Box";
import ToggleButton from '@mui/material/ToggleButton';
import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';

const Home: React.FC = () => {
  const frenchText = [
    "Cette application est un \"tracker\" de statistiques de joueurs d'un jeu en ligne. Elle détaille une partie de leurs statistiques individuelles, ainsi que de leurs matchs récents et du dernier tournoi joué.",
    "Caupanharm est un anagramme de \"capharnaüm\", synonyme de bazar. C'est aussi le nom de ce projet fourre-tout, sans but précis, pouvant évoluer à sa guise et changer de cap à tout moment tant qu'il me permet de continuer à me former en autonomie à de nouvelles technologies.",
    "Toute application hébergée sur ce dépot est donc successible de disparaître sans préavis.",
    "Le projet actuellement hébergé est une application web utilisant React 18, Vite et Typescript. Les données qu'il affiche sont pour l'instant hardcodées."
  ]

  const englishText = [
    "This app is a stats tracker for online players. It displays some of their individual statistics, as well as their recent matches and latest bracket.",
    "Caupanharm is an anagram of 'capharnaüm', a french word for 'mess'. It is also this project's name, that has no particular objective, and might change radically while I keep teaching myself various technologies.",
    "As such, any app hosted on this page may disappear with no prior warning.",
    "The current hosted project is a web app that uses React 18, Vite and Typescript. Its data is for now hardcoded."
  ]

  const [alignment, setAlignment] = useState('fr-FR');

  const handleChange = (
    event: React.MouseEvent<HTMLElement>,
    newAlignment: string,
  ) => {
    setAlignment(newAlignment);
  };

  return (
    <Box
      sx={{
        display: 'flex',
        flexDirection: 'column',
        justifyContent: "center",
        alignItems: "center",
        p: 2,
        marginLeft: "128px",
        marginRight: "128px",
        gap: 6
      }}
    >
      <ToggleButtonGroup
      size="small"
      color="primary"
      value={alignment}
      exclusive
      onChange={handleChange}
      aria-label="Language"
    >
      <ToggleButton value="fr-FR">Français</ToggleButton>
      <ToggleButton value="en-GB">English</ToggleButton>
    </ToggleButtonGroup>
      <div style={{marginTop: "64px", textAlign: "center" }}>
        {(alignment == "fr-FR") ? frenchText[0] : englishText[0]}
      </div>
      <div style={{ textAlign: "center"}}>
      {(alignment == "fr-FR") ? frenchText[1] : englishText[1]}
      </div>
      <div style={{ textAlign: "center", fontWeight: "bold" }}>
      {(alignment == "fr-FR") ? frenchText[2] : englishText[2]}
      </div>
      <div style={{textAlign: "center"}}>
      {(alignment == "fr-FR") ? frenchText[3] : englishText[3]}
      </div>
    </Box>
  );
};

export default Home;
