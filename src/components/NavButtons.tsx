import React, { FC, useState, useEffect } from "react";
import Button from "@mui/material/Button";
import NavigateBeforeIcon from "@mui/icons-material/NavigateBefore";
import NavigateNextIcon from "@mui/icons-material/NavigateNext";
import { NavButtonsProps } from "../interfaces/Interfaces";

export default function NavButtons({
  page,
  setPage,
  dataSize,
  pageSize,
}: NavButtonsProps) {
  const [prevButtonState, setPrevButtonState] = useState<boolean>(true);
  const [nextButtonState, setNextButtonState] = useState<boolean>(false);

  useEffect(() => {
    if(page == 0) setPrevButtonState(false)
    if (dataSize > page * pageSize) setNextButtonState(true);
  }, []);

  const handleDisplay = (event) => {
    console.log("---------")
    console.log("data size: ", dataSize)
    console.log("page size: ", pageSize)
    console.log("initial page: ", page)
    const requestedPage = (event.target.id === "prev") ? page-1 : page+1
    const lastPageSize = dataSize%pageSize
    const numberOfPages = (lastPageSize > 0) ? Math.floor(dataSize/pageSize) + 1 : Math.floor(dataSize/pageSize)

    if(requestedPage < 0) {
        setPage(0)
        console.log("final page: ", 0 )
    }else if(requestedPage >= numberOfPages) {
        setPage(numberOfPages-1)
        console.log("final page: ", numberOfPages-1)
    }else{
        setPage(requestedPage)
        setPrevButtonState(requestedPage > 0)
        setNextButtonState(requestedPage < numberOfPages-1)
        console.log("final page: ", requestedPage)
    }
  };

  return (
    <div style={{ display: "flex", justifyContent: "space-between" }}>
      <Button
        id="prev"
        onClick={handleDisplay}
        disabled={!prevButtonState}
        variant="contained"
        startIcon={<NavigateBeforeIcon />}
      >
        Page précédente
      </Button>
      Page {page+1}/{Math.floor(dataSize/pageSize)+1}
      <Button
        id="next"
        onClick={handleDisplay}
        disabled={!nextButtonState}
        variant="contained"
        endIcon={<NavigateNextIcon />}
      >
        Page suivante
      </Button>
    </div>
  );
}
