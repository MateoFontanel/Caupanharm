import axios from "axios";
import {AdditionalPlayerDataInterface, BracketMatchInterface, PlayerMatchesInterface} from "./interfaces"

export const fetchBracket = async () => {
  const data = await axios.get<BracketMatchInterface[]>("http://localhost:8080/api/bracket");
  return data.data;
};

export const fetchPlayers = async () => {
  const data = await axios.get<PlayerMatchesInterface[]>("http://localhost:8080/api/players");
  return data.data;
};

export const fetchStats = async () => {
  const data = await axios.get<AdditionalPlayerDataInterface[]>("http://localhost:8080/api/stats");
  return data.data;
};
