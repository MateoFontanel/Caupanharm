import React, { FC } from "react";
import { styled } from "@mui/material/styles";
import ArrowForwardIosSharpIcon from "@mui/icons-material/ArrowForwardIosSharp";
import MuiAccordion from "@mui/material/Accordion";
import MuiAccordionSummary from "@mui/material/AccordionSummary";
import MuiAccordionDetails from "@mui/material/AccordionDetails";
import Typography from "@mui/material/Typography";
import {
  AccordionMatchElementProps,
  V1LifetimeMatchItem,
} from "../../interfaces/HenrikInterfaces";
import MatchSummary from "./MatchSummary";

const Accordion = styled((props: any) => (
  <MuiAccordion disableGutters elevation={0} square {...props} />
))(({ theme }) => ({
  border: `1px solid ${theme.palette.divider}`,
  "&:not(:last-child)": {
    borderBottom: 0,
  },
  "&::before": {
    display: "none",
  },
}));

const AccordionSummary = styled((props: any) => (
  <MuiAccordionSummary
    expandIcon={<ArrowForwardIosSharpIcon sx={{ fontSize: "0.9rem" }} />}
    {...props}
  />
))(({ theme }) => ({
  backgroundColor:
    theme.palette.mode === "dark"
      ? "rgba(255, 255, 255, .05)"
      : "rgba(0, 0, 0, .03)",
  flexDirection: "row-reverse",
  "& .MuiAccordionSummary-expandIconWrapper.Mui-expanded": {
    transform: "rotate(90deg)",
  },
  "& .MuiAccordionSummary-content": {
    marginLeft: theme.spacing(1),
  },
}));

const AccordionDetails = styled(MuiAccordionDetails)(({ theme }) => ({
  padding: theme.spacing(2),
  borderTop: "1px solid rgba(0, 0, 0, .125)",
}));

const AccordionMatchElement: FC<AccordionMatchElementProps> = ({
  id,
  data,
  expanded,
  onChange,
}) => {
  return (
    <Accordion
      key={id}
      expanded={expanded === `panel${id}`}
      onChange={onChange(`panel${id}`)}
    >
      <AccordionSummary
        aria-controls={`panel${id}d-content`}
        id={`panel${id}d-header`}
        sx={{
          backgroundImage: `url(src/assets/images/maps/Loading_Screen_${data.meta.map.name}.jpg)`,
          backgroundSize: "cover",
          backgroundPosition: "center",
          color: "white",
          fontWeight: "bold",
          "& .MuiTypography-root": {
            color: "white",
            fontWeight: "bold",
          },
          "& .MuiSvgIcon-root": {
            color: "white",
          },
        }}
      >
        <Typography>{formatSummary(data)}</Typography>
      </AccordionSummary>
      <AccordionDetails>
        <MatchSummary data={data}/>
      </AccordionDetails>
    </Accordion>
  );
};

function formatSummary(data: V1LifetimeMatchItem) {
  const dateItems = data.meta.started_at.split("T")[0].split("-"); // YYYY-MM-DDTHH:mm:ss.sssZ --> [YYYY,MM,DD]
  const formattedDate = dateItems[2] + "/" + dateItems[1];

  let allyScore: number, enemyScore: number;
  let formattedResult: string = "";

  if (data.teams.red != undefined && data.teams.blue != undefined) {
    if (data.stats.team.toLowerCase() === "red") {
      allyScore = data.teams.red;
      enemyScore = data.teams.blue;
    } else {
      allyScore = data.teams.blue;
      enemyScore = data.teams.red;
    }

    if (allyScore == enemyScore) {
      formattedResult = `Egalité ${allyScore}-${enemyScore}`;
    } else if (allyScore > enemyScore) {
      formattedResult = `Victoire ${allyScore}-${enemyScore}`;
    } else {
      formattedResult = `Défaite ${allyScore}-${enemyScore}`;
    }
  }

  return `${formattedDate} - ${data.meta.map.name} - ${formattedResult}`;
}

export default AccordionMatchElement;
