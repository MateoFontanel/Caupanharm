import React, { FC, useEffect, useState } from "react";
import AppBar from "@mui/material/AppBar";
import Tab from "@mui/material/Tab";
import Tabs from "@mui/material/Tabs";
import { Toolbar, InputBase, IconButton } from "@mui/material";
import HomeIcon from "@mui/icons-material/Home";
import GamesIcon from "@mui/icons-material/Games";
import GroupsIcon from "@mui/icons-material/Groups";
import CalendarMonthIcon from "@mui/icons-material/CalendarMonth";

import { Link, Outlet, useLocation, useNavigate } from "@tanstack/react-router";
import SearchIcon from "@mui/icons-material/Search";

const Navbar: FC = () => {
  const [inputValue, setInputValue] = useState("");
  const location = useLocation();
  const [tabValue, setTabValue] = React.useState("home");
  const navigate = useNavigate();

  useEffect(() => {
    const pathToValueMap = {
      "/": "home",
      "/players": "players",
      "/matches": "matches",
      "/planning": "planning",
      "/profile": false,
    };
    setTabValue(pathToValueMap[location.pathname]);
  }, [location]);

  const handleChange = (event, newValue) => {
    setTabValue(newValue);
  };

  const handleInputChange = (event) => {
    setInputValue(event.target.value);
  };

  const handleSearch = () => {
    if (inputValue.trim() && inputValue.includes("#")) {
      setTabValue(false) // not an error, API says false is allowed
      navigate({ to: `/profile?username=${encodeURIComponent(inputValue)}` });
    }
    setInputValue('')

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
          <Tabs
            value={tabValue}
            onChange={handleChange}
            selectionFollowsFocus={false}
            centered
            aria-label="navbar tabs"
          >
            <Tab
              value="home"
              icon={<HomeIcon />}
              label="Accueil"
              component={Link}
              to="/"
            />
            <Tab
              value="players"
              icon={<GroupsIcon />}
              label="Joueurs (demo)"
              component={Link}
              to="/players"
            />
            <Tab
              value="matches"
              icon={<GamesIcon />}
              label="Bracket (demo)"
              component={Link}
              to="/matches"
            />
            <Tab
              value="planning"
              icon={<CalendarMonthIcon />}
              label="Planning"
              disabled
            />
            <Tab
              value="player"
              disabled
            />
          </Tabs>
          <InputBase
            value={inputValue}
            onChange={handleInputChange}
            required
            placeholder="Chercher un joueur..."
            inputProps={{ "aria-label": "rechercher" }}
            sx={{ ml: 8, flexGrow: 1 }}
          />
          <IconButton
            type="submit"
            sx={{ p: "10px" }}
            aria-label="search"
            onClick={handleSearch}
          >
            <SearchIcon />
          </IconButton>
        </Toolbar>
      </AppBar>
      <Outlet />
    </>
  );
};

export default Navbar;
