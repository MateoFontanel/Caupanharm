import React, {FC} from "react"
import Box from "@mui/material/Box";

const Loader: FC = () => {
  return(
    <Box
        sx={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          marginTop: "128px",
        }}
      >
        <img src={"src/assets/images/loading.gif"} alt="Loading..." />
    </Box>
)}

export default Loader;