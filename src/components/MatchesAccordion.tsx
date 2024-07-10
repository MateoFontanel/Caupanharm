import React, { FC, useState } from 'react';
import { styled } from '@mui/material/styles';
import ArrowForwardIosSharpIcon from '@mui/icons-material/ArrowForwardIosSharp';
import MuiAccordion from '@mui/material/Accordion';
import MuiAccordionSummary from '@mui/material/AccordionSummary';
import MuiAccordionDetails from '@mui/material/AccordionDetails';
import Typography from '@mui/material/Typography';
import PropTypes from 'prop-types';
import Box from '@mui/material/Box';

interface Match {
  //TODO
}

interface MatchesAccordionProps {
  //TODO
}

const Accordion = styled((props: any) => (
  <MuiAccordion disableGutters elevation={0} square {...props} />
))(({ theme }) => ({
  border: `1px solid ${theme.palette.divider}`,
  '&:not(:last-child)': {
    borderBottom: 0,
  },
  '&::before': {
    display: 'none',
  },
}));

const AccordionSummary = styled((props: any) => (
  <MuiAccordionSummary
    expandIcon={<ArrowForwardIosSharpIcon sx={{ fontSize: '0.9rem' }} />}
    {...props}
  />
))(({ theme }) => ({
  backgroundColor:
    theme.palette.mode === 'dark'
      ? 'rgba(255, 255, 255, .05)'
      : 'rgba(0, 0, 0, .03)',
  flexDirection: 'row-reverse',
  '& .MuiAccordionSummary-expandIconWrapper.Mui-expanded': {
    transform: 'rotate(90deg)',
  },
  '& .MuiAccordionSummary-content': {
    marginLeft: theme.spacing(1),
  },
}));

const AccordionDetails = styled(MuiAccordionDetails)(({ theme }) => ({
  padding: theme.spacing(2),
  borderTop: '1px solid rgba(0, 0, 0, .125)',
}));

const MatchesAccordion: FC<MatchesAccordionProps> = ({ playerId, data }) => {
  const [expanded, setExpanded] = useState<string | false>();

  const handleChange = (panel: string) => (
    event: React.SyntheticEvent,
    newExpanded: boolean
  ) => {
    setExpanded(newExpanded ? panel : false);
  };

  console.log(data[playerId]);
  return (
    <Box sx={{ width: '100%' }}>
      {data[playerId].matches.map((match, index) => (
        <Accordion
          key={index}
          expanded={expanded === `panel${index}`}
          onChange={handleChange(`panel${index}`)}
        >
          <AccordionSummary
            aria-controls={`panel${index}d-content`}
            id={`panel${index}d-header`}
            sx={{
              backgroundImage: `url(src/assets/images/maps/Loading_Screen_${match.map}.jpg)`,
              backgroundSize: 'cover',
              backgroundPosition: 'center',
              color: 'white',
              fontWeight: 'bold',
              '& .MuiTypography-root': {
                color: 'white',
                fontWeight: 'bold',
              },
              '& .MuiSvgIcon-root': {
                color: 'white',
              },
            }}
          >
            <Typography>{`${match.date} - ${match.map} - ${
              match.result === 'win' ? 'Victoire' : 'Défaite'
            }`}</Typography>
          </AccordionSummary>
          <AccordionDetails>
            <Typography>{`Score : ${match.allyScore} - ${match.enemyScore}`}</Typography>
          </AccordionDetails>
        </Accordion>
      ))}
    </Box>
  );
};

MatchesAccordion.propTypes = {
  playerId: PropTypes.number,
  data: PropTypes.array,
};

export default MatchesAccordion;