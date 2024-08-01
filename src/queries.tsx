import axios from "axios";
import {AdditionalPlayerDataInterface, BracketMatchInterface, PlayerMatchesInterface} from "./interfaces/Interfaces"
import { HenrikErrorsInterface, V1LifetimeMatches} from "./interfaces/HenrikInterfaces"
export const fetchBracket = async () => {
  console.log("Querying http://localhost:8080/api/bracket")
  const data = await axios.get<BracketMatchInterface[]>("http://localhost:8080/api/bracket");
  return data.data;
};

export const fetchPlayers = async () => {
  console.log("Querying http://localhost:8080/api/players")
  const data = await axios.get<PlayerMatchesInterface[]>("http://localhost:8080/api/players");
  return data.data;
};

export const fetchStats = async () => {
  console.log("Querying http://localhost:8080/api/stats")
  const data = await axios.get<AdditionalPlayerDataInterface[]>("http://localhost:8080/api/stats");
  return data.data;
};

export const fetchPlayerMatches = async (username: string) => {
  console.log(`Querying http://localhost:8080/api/matches/${username.split('#')[0]}/${username.split('#')[1]}`)
  const data = await axios.get<V1LifetimeMatches | HenrikErrorsInterface>(`http://localhost:8080/api/matches/${username.split('#')[0]}/${username.split('#')[1]}`)
  return data.data;
}
