import React from 'react';
import Box from '@mui/material/Box';

const Lorem: React.FC = () => {
  return (
    <Box
      sx={{
        display: "block",
        justifyContent: "center",
        alignItems: "center",
        p: 2,
        marginTop: "72px",
        marginLeft: "128px",
        marginRight: "128px"
      }}
    >
      <div style={{ marginBottom: '64px' }}>
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam
        ornare arcu et nisl congue, mattis viverra nisl pellentesque. In
        in sapien finibus, fringilla ligula quis, aliquam erat. Vestibulum
        ante ipsum primis in faucibus orci luctus et ultrices posuere
        cubilia curae; Quisque dignissim ut lorem sed pellentesque.
        Phasellus turpis velit, auctor ut lobortis eget, tempus sed felis.
        Aliquam pulvinar felis non arcu tristique, eu sagittis metus
        laoreet. Praesent laoreet neque massa, non vehicula ligula lacinia
        ac. Donec at faucibus dui, vitae fringilla purus. Quisque
        fringilla mauris eu orci sollicitudin laoreet. In lacinia turpis
        eget mi ultricies ultrices. Duis sed risus vitae tellus porta
        vehicula id id lectus.
      </div>
      <div>
        Nam quis bibendum dui. Quisque vel porttitor ante, ac rutrum
        tortor. Nunc ac lacus vitae ligula commodo laoreet eu in neque.
        Proin diam ex, ultricies eu mauris scelerisque, egestas porta
        urna. Class aptent taciti sociosqu ad litora torquent per conubia
        nostra, per inceptos himenaeos. In justo ante, consectetur quis
        lectus et, venenatis fermentum odio. Morbi cursus ut arcu
        condimentum finibus.
      </div>
    </Box>
  );
}

export default Lorem;