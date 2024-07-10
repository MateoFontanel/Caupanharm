import React, { useState } from "react";
import NavBar from "./NavBar";
import Lorem from "./Lorem";
import Players from "./Players";
import Matches from "./Matches";
import Box from "@mui/material/Box";

function App() {
  const [nav, setNav] = useState<string>("home");

  const handleChange = (event: React.ChangeEvent<{}>, newValue: string) => {
    setNav(newValue);
  };

  const renderContent = (): JSX.Element => {
    switch (nav) {
      case "home":
        return <Lorem />;
      case "players":
        return <Players />;
      case "matches":
        return <Matches />;
      case "planning":
        return <Lorem />;
      default:
        return <Lorem />;
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