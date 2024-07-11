import React, { useState } from "react";
import NavBar from "./NavBar";
import Home from "./Home";
import Players from "./Players";
import Matches from "./Matches";
import Box from "@mui/material/Box";

function App() {
  const [nav, setNav] = useState<string>("home");

  const handleChange = (event: React.SyntheticEvent, newValue: string) => {
    setNav(newValue);
  };

  const renderContent = () => {
    switch (nav) {
      case "home":
        return <Home />;
      case "players":
        return <Players />;
      case "matches":
        return <Matches />;
      case "planning":
        return <Home />;
      default:
        return <Home />;
    }
  };

  return (
    <Box sx={{ minHeight: '100vh', minWidth: '100vw' }}>
      <NavBar value={nav} handleChange={handleChange} />
      {renderContent()}
    </Box>
  );
}

export default App;