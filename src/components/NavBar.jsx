import AppBar from "@mui/material/AppBar";
import Tab from "@mui/material/Tab";
import Tabs from "@mui/material/Tabs";
import { Toolbar } from "@mui/material";

import HomeIcon from '@mui/icons-material/Home';
import GamesIcon from '@mui/icons-material/Games';
import GroupsIcon from '@mui/icons-material/Groups';
import CalendarMonthIcon from '@mui/icons-material/CalendarMonth';

import PropTypes from "prop-types";

export default function Navbar({value, handleChange}) {
  return (
    <>
      <AppBar
        sx={{
          position:"sticky",
          boxShadow: 0,
          bgcolor: "white",
          alignItems: "center",
          borderBottom: "1px solid",
        }}
      >
        <Toolbar>
          <Tabs value={value} onChange={handleChange} centered aria-label="navbar tabs">
            <Tab value="home" icon={<HomeIcon/>} label="Accueil" />
            <Tab value="players" icon={<GroupsIcon/>} label="Joueurs" />
            <Tab value="matches" icon={<GamesIcon/>} label="Matches"/>
            <Tab value="planning" icon={<CalendarMonthIcon/>} label="Planning" disabled/>
          </Tabs>
        </Toolbar>
      </AppBar>
    </>
  );
}

Navbar.propTypes = {
  value: PropTypes.string,
  handleChange: PropTypes.func,
};