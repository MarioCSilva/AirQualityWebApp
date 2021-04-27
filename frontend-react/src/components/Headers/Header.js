import React, { useEffect, useState } from "react";
// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
import { useTheme } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Container from "@material-ui/core/Container";
import Grid from "@material-ui/core/Grid";
// @material-ui/icons components
import TrendingUpIcon from '@material-ui/icons/TrendingUp';
import TimerIcon from '@material-ui/icons/Timer';
import CheckIcon from '@material-ui/icons/Check';
import ClearIcon from '@material-ui/icons/Clear';
import AirQualityService from "../../services/AirQualityService";

// core components
import CardStats from "components/Cards/CardStats.js";

import componentStyles from "assets/theme/components/header.js";

const useStyles = makeStyles(componentStyles);


const Header = (update) => {
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
  }, [update])

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
                  icon={CheckIcon}
                  color="bgInfo"
                />
              </Grid>
              <Grid item xl={3} lg={6} xs={12}>
                <CardStats
                  subtitle="Misses"
                  title={misses}
                  icon={ClearIcon}
                  color="bgWarning"
                />
              </Grid>
              <Grid item xl={3} lg={6} xs={12}>
                <CardStats
                  subtitle="Time To Live"
                  title={timeToLive}
                  icon={TimerIcon}
                  color="bgWarningLight"
                />
              </Grid>
              <Grid item xl={3} lg={6} xs={12}>
                <CardStats
                  subtitle="Total Requests"
                  title={totalChecks}
                  icon={TrendingUpIcon}
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
