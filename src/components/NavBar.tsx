import React, { FC, useEffect, useState } from "react";
import AppBar from "@mui/material/AppBar";
import Tab from "@mui/material/Tab";
import Tabs from "@mui/material/Tabs";
import { Toolbar, InputBase, IconButton } from "@mui/material";
import HomeIcon from "@mui/icons-material/Home";
import GamesIcon from "@mui/icons-material/Games";
import GroupsIcon from "@mui/icons-material/Groups";
import CalendarMonthIcon from "@mui/icons-material/CalendarMonth";
import { Box } from "@mui/material";

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

  const handleKeyDown = (event) => {
    if (event.key === 'Enter') {
      handleSearch();
    }
  };

  const handleSearch = () => {
    if (inputValue.trim() && inputValue.includes("#")) {
      setTabValue(false); // not an error, API says false is allowed
      const searchParams = {username: inputValue}
      navigate({to: "/profile", search: searchParams });
    }
    setInputValue("");
  };

  return (
    <>
      <AppBar sx={{position: 'sticky', boxShadow: 0, bgcolor: 'white', borderBottom: '1px solid' }}>
      <Toolbar sx={{display: 'flex', justifyContent: 'space-between', width: '96.6%', px: 2, position: "relative"}}>
        <Box sx={{flexGrow: 1, display: 'flex', justifyContent: 'center', position: 'absolute',left: '50%', transform: 'translateX(-47%)'}}>
          <Tabs value={tabValue} onChange={handleChange} selectionFollowsFocus={false} aria-label="navbar tabs" >
            <Tab value="home" icon={<HomeIcon />} label="Accueil" component={Link} to="/" />
            <Tab value="players" icon={<GroupsIcon />} label="Joueurs (demo)" component={Link} to="/players" />
            <Tab value="matches" icon={<GamesIcon />} label="Bracket (demo)" component={Link} to="/matches" />
            <Tab value="planning" icon={<CalendarMonthIcon />} label="Planning" disabled />
          </Tabs>
        </Box>
        <Box sx={{ display: 'flex', alignItems: 'center', marginLeft: "auto", marginRight: "5%" }}>
          <InputBase value={inputValue} onChange={handleInputChange} onKeyDown={handleKeyDown} required placeholder="Chercher un joueur..." inputProps={{ 'aria-label': 'rechercher' }} sx={{ ml: 2 }}/>
          <IconButton type="submit" sx={{ p: '10px' }} aria-label="search" onClick={handleSearch}>
            <SearchIcon />
          </IconButton>
        </Box>
      </Toolbar>
    </AppBar>
      <Outlet />
    </>
  );
};

export default Navbar;
