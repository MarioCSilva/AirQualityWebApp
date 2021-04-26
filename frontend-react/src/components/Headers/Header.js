import React, { useEffect, useState } from "react";
// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
import { useTheme } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Container from "@material-ui/core/Container";
import Grid from "@material-ui/core/Grid";
// @material-ui/icons components
import EmojiEvents from "@material-ui/icons/EmojiEvents";
import GroupAdd from "@material-ui/icons/GroupAdd";
import InsertChartOutlined from "@material-ui/icons/InsertChartOutlined";
import PieChart from "@material-ui/icons/PieChart";
import AirQualityService from "../../services/AirQualityService";

// core components
import CardStats from "components/Cards/CardStats.js";

import componentStyles from "assets/theme/components/header.js";

const useStyles = makeStyles(componentStyles);


const Header = () => {
  const classes = useStyles();
  const [errorMessage, setErrorMessage] = useState('');
  const [hits, setHits] = useState(0);
  const [misses, setMisses] = useState(0);
  const [timeToLive, setTimeToLive] = useState(0);
  const [totalChecks, setTotalChecks] = useState(0);

  useEffect(() => {
    AirQualityService.getCacheAirQuality().then((res) => {
      console.log(res)
      setHits(res.hits);
      setMisses(res.misses);
      setTimeToLive(res.timeToLive);
      setTotalChecks(res.totalChecks);
      setErrorMessage(null);
    }).catch((err) => {
      setErrorMessage("Rest API Unavailable");
    })
  }, [])

  const theme = useTheme();
  return (
    <>
    { errorMessage ? errorMessage : 
      <div className={classes.header}>
        <Container
          maxWidth={false}
          component={Box}
          classes={{ root: classes.containerRoot }}
        >
          <div>
            <Grid container>
              <Grid item xl={3} lg={6} xs={12}>
                <CardStats
                  subtitle={"Hits"}
                  title={hits}
                  icon={InsertChartOutlined}
                  color="bgError"
                />
              </Grid>
              <Grid item xl={3} lg={6} xs={12}>
                <CardStats
                  subtitle="Misses"
                  title={misses}
                  icon={PieChart}
                  color="bgWarning"
                />
              </Grid>
              <Grid item xl={3} lg={6} xs={12}>
                <CardStats
                  subtitle="Time To Live"
                  title={timeToLive}
                  icon={GroupAdd}
                  color="bgWarningLight"
                />
              </Grid>
              <Grid item xl={3} lg={6} xs={12}>
                <CardStats
                  subtitle="Total Cache Checks"
                  title={totalChecks}
                  icon={EmojiEvents}
                  color="bgInfo"
                />
              </Grid>
            </Grid>
          </div>
        </Container>
      </div>
      }
    </>
  );
};

export default Header;
