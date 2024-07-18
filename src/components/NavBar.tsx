import React, { FC, useEffect } from "react";
import AppBar from "@mui/material/AppBar";
import Tab from "@mui/material/Tab";
import Tabs from "@mui/material/Tabs";
import { Toolbar } from "@mui/material";

import HomeIcon from "@mui/icons-material/Home";
import GamesIcon from "@mui/icons-material/Games";
import GroupsIcon from "@mui/icons-material/Groups";
import CalendarMonthIcon from "@mui/icons-material/CalendarMonth";

import { Link, Outlet, useLocation } from "@tanstack/react-router";

const Navbar: FC = () => {
  const location = useLocation();
  const [value, setValue] = React.useState("home");

  useEffect(() => {
    const pathToValueMap = {
      "/": "home",
      "/players": "players",
      "/matches": "matches",
      "/planning": "planning"
    };

    // Set the active tab based on current location pathname
    setValue(pathToValueMap[location.pathname]);
  }, [location]);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <>
      <AppBar
        sx={{
          position: "sticky",
          boxShadow: 0,
          bgcolor: "white",
          alignItems: "center",
          borderBottom: "1px solid",
        }}
      >
        <Toolbar>
          <Tabs value={value} onChange={handleChange} centered aria-label="navbar tabs">
            <Tab value="home" icon={<HomeIcon />} label="Accueil" component={Link} to="/"/>
            <Tab value="players" icon={<GroupsIcon />} label="Joueurs" component={Link} to="/players"/>
            <Tab value="matches" icon={<GamesIcon />} label="Matches" component={Link} to="/matches"/>
            <Tab value="planning" icon={<CalendarMonthIcon />} label="Planning" disabled/>
          </Tabs>
        </Toolbar>
      </AppBar>
      <Outlet />
    </>
  );
};

export default Navbar;
