import axios from "axios";
import {BracketMatchInterface} from "./interfaces"

export const fetchBracket = async () => {
  const data = await axios.get<BracketMatchInterface[]>("http://localhost:8080/api/bracket");
  await new Promise(r => setTimeout(r, 2000));
  return data.data;
};
