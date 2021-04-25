import React, { useState } from "react";
import { makeStyles } from "@material-ui/core/styles";
import { useTheme } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";
import Container from "@material-ui/core/Container";
import FormControl from "@material-ui/core/FormControl";
import FormGroup from "@material-ui/core/FormGroup";
import Grid from "@material-ui/core/Grid";
import InputLabel from "@material-ui/core/InputLabel";
import Search from "@material-ui/icons/Search";
import UserHeader from "components/Headers/UserHeader.js";
import InputBase from "@material-ui/core/InputBase";
import componentStyles from "assets/theme/components/admin-navbar.js";
import componentStylesCity from "assets/theme/views/admin/profile.js";
import Typography from "@material-ui/core/Typography";
import AirQualityService from "../../services/AirQualityService.js";
import LocationOn from "@material-ui/icons/LocationOn";

const useStyles = makeStyles(componentStyles);
const useStylesCity = makeStyles(componentStylesCity);

function SearchAirqualityView() {
  const [cityAirQuality, setCityAirQuality] = useState(null);
  const [errorMessage, setErrorMessage] = useState('');
  const classes = useStyles();
  const classesCity = useStylesCity();
  const theme = useTheme();

  const searchAirQuality = () => {
    const city = document.getElementById('city').value ;
    const country = document.getElementById('country').value;

    if (city != '') {
      if (country != '') {
        AirQualityService.getCityAirQuality(city, country).then((res) => {
          setCityAirQuality(res);
          setErrorMessage(null);
        }).catch((err) => {
          console.log(err)
          setCityAirQuality(null);
          setErrorMessage("Couldn't find city or service unavailable");
        })
      } else {
        AirQualityService.getCityAirQuality(city).then((res) => {
          setCityAirQuality(res);
          setErrorMessage(null);
        }).catch((err) => {
        console.log(err)
        setCityAirQuality(null);
        setErrorMessage("Couldn't find city or service unavailable")
      })
      }
    } else {
      setErrorMessage(null);
      setCityAirQuality(null);
    }
  }

  return (
    <>
      <UserHeader />
      {/* Page content */}
      <Container
        maxWidth={false}
        component={Box}
        marginTop="-6rem"
        classes={{ root: classes.containerRoot }}
      >
        <Grid container>
          <Grid
            item
            xs={12}
            component={Box}
            marginBottom="3rem"
            classes={{ root: classes.gridItemRoot + " " + classes.order2 }}
          >
            <Card
              classes={{
                root: classes.cardRoot + " " + classes.cardRootSecondary,
              }}
            >
              <CardContent>

                <div className={classes.plLg4} style={{paddingTop: 20}}>
                  <Grid container>
                    <Grid item xs={12} lg={6}>
                      <FormGroup>
                        <FormControl variant="outlined" fullWidth>
                            <InputLabel htmlFor="outlined-adornment-search-responsive">
                                City
                            </InputLabel>
                            <Box
                              display="flex"
                              alignItems="center"
                              width="auto"
                              marginRight="1rem"
                              classes={{
                                root: classes.searchBox,
                              }}
                            >
                              <Search className={classes.searchIcon} />
                              <InputBase
                                classes={{
                                  input: classes.searchInput,
                                }}
                                id='city'
                              />
                            </Box>
                        </FormControl>
                      </FormGroup>
                    </Grid>
                    <Grid item xs={12} lg={6}>
                      <FormGroup>
                        <FormControl variant="outlined" fullWidth>
                            <InputLabel htmlFor="outlined-adornment-search-responsive">
                                Country
                            </InputLabel>
                            <Box
                                display="flex"
                                alignItems="center"
                                width="auto"
                                marginRight="1rem"
                                classes={{
                                  root: classes.searchBox,
                                }}
                              >
                                <Search className={classes.searchIcon} />
                                <InputBase
                                  classes={{
                                    input: classes.searchInput,
                                  }}
                                  id='country'
                                />
                              </Box>
                        </FormControl>
                      </FormGroup>
                    </Grid>
                  </Grid>
                </div>
                <Box textAlign="center">
                  <Button
                    color="inherit"
                    classes={{
                      label: classes.buttonLabel,
                      root: classes.buttonRoot,
                    }}
                    onClick={() => searchAirQuality()}>
                      Search
                  </Button>
                </Box>
              </CardContent>
            </Card>
          </Grid>
        </Grid>
        { errorMessage ?
          <Box component={Grid} container justifyContent="center" style={{paddingTop:50}}>
            <Box textAlign="center">
              <Box
                component={Typography}
                variant="h1"
                fontWeight="500!important"
                display="flex"
                justifyContent="center"
              >
                <Box
                  width="2rem!important"
                  height="2rem!important"
                  style={{color: 'darkBlue'}}
                ></Box>
                <Typography variant="h1" style={{color: 'blue'}}>
                {errorMessage}
              </Typography>

              </Box>
            </Box>
            </Box>
         : ''}
        { cityAirQuality ? 
            <Card classes={{ root: classesCity.cardRoot }} style={{marginTop:50}}>
              <Box component={Grid} container justifyContent="center" style={{paddingTop:50}}>
              
                <Box textAlign="center">
                  <Box
                    component={Typography}
                    variant="h1"
                    fontWeight="500!important"
                    display="flex"
                    justifyContent="center"
                  >
                    <Box
                      component={LocationOn}
                      width="2rem!important"
                      height="2rem!important"
                      style={{color: 'darkBlue'}}
                    ></Box>
                    <Typography variant="h1" style={{color: 'blue'}}>
                    {cityAirQuality.city_name}
                    {', ' + cityAirQuality.country_code}
                  </Typography>

                  </Box>
                </Box>
                </Box>
                <Box component={Grid} container justifyContent="center">
                <Box textAlign="center">
                  <Box
                    component={Typography}
                    variant="h3"
                    fontWeight="300!important"
                    display="flex"
                    justifyContent="center"
                  >
                    <Typography variant="h3" style={{paddingRight: 2}}>
                      Longitude:
                    </Typography>
                    <Typography variant="h3" style={{paddingRight: 10, color: 'blue'}}>
                      {cityAirQuality.lon}
                    </Typography>
                    <Typography variant="h3" style={{paddingRight: 2}}>
                    Latitude:
                    </Typography>
                    <Typography variant="h3" style={{paddingRight: 10, color: 'blue'}}>
                      {cityAirQuality.lat}
                    </Typography>
                  </Box>
                </Box>
                </Box>
                <Box component={Grid} container justifyContent="center">

                <Box textAlign="center">
                  <Box
                    component={Typography}
                    variant="h3"
                    fontWeight="300!important"
                    display="flex"
                    justifyContent="center"
                  >
                    <Typography variant="h3" style={{paddingRight: 2}}>
                    Timezone:
                    </Typography>
                    <Typography variant="h3" style={{paddingRight: 10, color: 'blue'}}>
                      {cityAirQuality.timezone}
                    </Typography>
                    <Typography variant="h3" style={{paddingRight: 2}}>
                    State Code:
                    </Typography>
                    <Typography variant="h3" style={{paddingRight: 10, color: 'blue'}}>
                      {cityAirQuality.state_code}
                    </Typography>
                  </Box>
                </Box>
              </Box>
                  
              <Box
                component={CardContent}
                classes={{ root: classesCity.ptMd4 }}
                paddingTop="0!important"
              >
                <Grid container>
                  <Grid item xs={12}>
                    <Box
                      padding="1rem 0"
                      justifyContent="center"
                      display="flex"
                      className={classesCity.mtMd5}
                    >
                      <Box
                        textAlign="center"
                        marginRight="1rem"
                        padding=".875rem"
                      >
                        <Box
                          component="span"
                          fontSize="1.1rem"
                          fontWeight="700"
                          display="block"
                          letterSpacing=".025em"
                          className={classesCity.typographyRootH6}
                          style={{color: 'blue'}}
                        >
                          {cityAirQuality.data[0].aqi}
                        </Box>
                        <Box
                          component="span"
                          fontSize=".875rem"
                        >
                          aqi
                        </Box>
                      </Box>
                      <Box
                        textAlign="center"
                        marginRight="1rem"
                        padding=".875rem"
                      >
                        <Box
                          component="span"
                          fontSize="1.1rem"
                          fontWeight="700"
                          display="block"
                          letterSpacing=".025em"
                          className={classesCity.typographyRootH6}
                          style={{color: 'blue'}}
                        >
                          {cityAirQuality.data[0].so2}
                        </Box>
                        <Box
                          component="span"
                          fontSize=".875rem"
                        >
                          so2
                        </Box>
                      </Box>
                      <Box textAlign="center" padding=".875rem">
                        <Box
                          component="span"
                          fontSize="1.1rem"
                          fontWeight="700"
                          display="block"
                          letterSpacing=".025em"
                          className={classesCity.typographyRootH6}
                          style={{color: 'blue'}}
                        >
                          {cityAirQuality.data[0].no2}
                        </Box>
                        <Box
                          component="span"
                          fontSize=".875rem"
                        >
                          no2
                        </Box>
                      </Box>
                      <Box textAlign="center" padding=".875rem">
                        <Box
                          component="span"
                          fontSize="1.1rem"
                          fontWeight="700"
                          display="block"
                          letterSpacing=".025em"
                          className={classesCity.typographyRootH6}
                          style={{color: 'blue'}}
                        >
                          {cityAirQuality.data[0].pm25}
                        </Box>
                        <Box
                          component="span"
                          fontSize=".875rem"
                        >
                          pm25
                        </Box>
                      </Box>
                      <Box textAlign="center" padding=".875rem">
                        <Box
                          component="span"
                          fontSize="1.1rem"
                          fontWeight="700"
                          display="block"
                          letterSpacing=".025em"
                          className={classesCity.typographyRootH6}
                          style={{color: 'blue'}}
                        >
                          {cityAirQuality.data[0].o3}
                        </Box>
                        <Box
                          component="span"
                          fontSize=".875rem"
                        >
                          o3
                        </Box>
                      </Box>
                      <Box textAlign="center" padding=".875rem">
                        <Box
                          component="span"
                          fontSize="1.1rem"
                          fontWeight="700"
                          display="block"
                          letterSpacing=".025em"
                          className={classesCity.typographyRootH6}
                          style={{color: 'blue'}}
                        >
                          {cityAirQuality.data[0].co}
                        </Box>
                        <Box
                          component="span"
                          fontSize=".875rem"
                        >
                          co
                        </Box>
                      </Box>
                    </Box>
                  </Grid>
                </Grid>
              </Box>
              <Box
                component={CardContent}
                classes={{ root: classesCity.ptMd4 }}
                paddingTop="0!important"
              >
                <Grid container>
                  <Grid item xs={12}>
                    <Box
                      padding="1rem 0"
                      justifyContent="center"
                      display="flex"
                      className={classesCity.mtMd5}
                    >
                      <Box textAlign="center" padding=".875rem">
                        <Box
                          component="span"
                          fontSize="1.1rem"
                          fontWeight="700"
                          display="block"
                          letterSpacing=".025em"
                          className={classesCity.typographyRootH6}
                          style={{color: 'blue'}}
                        >
                          {cityAirQuality.data[0].pm10}
                        </Box>
                        <Box
                          component="span"
                          fontSize=".875rem"
                        >
                          pm10
                        </Box>
                      </Box>
                      <Box textAlign="center" padding=".875rem">
                        <Box
                          component="span"
                          fontSize="1.1rem"
                          fontWeight="700"
                          display="block"
                          letterSpacing=".025em"
                          className={classesCity.typographyRootH6}
                          style={{color: 'blue'}}
                        >
                          {cityAirQuality.data[0].mold_level}
                        </Box>
                        <Box
                          component="span"
                          fontSize=".875rem"
                        >
                          Mold Level
                        </Box>
                      </Box>
                      <Box textAlign="center" padding=".875rem">
                        <Box
                          component="span"
                          fontSize="1.1rem"
                          fontWeight="700"
                          display="block"
                          letterSpacing=".025em"
                          className={classesCity.typographyRootH6}
                          style={{color: 'blue'}}
                        >
                          {cityAirQuality.data[0].pollen_level_tree}
                        </Box>
                        <Box
                          component="span"
                          fontSize=".875rem"
                        >
                          Pollen Level Tree
                        </Box>
                      </Box>
                      <Box textAlign="center" padding=".875rem">
                        <Box
                          component="span"
                          fontSize="1.1rem"
                          fontWeight="700"
                          display="block"
                          letterSpacing=".025em"
                          className={classesCity.typographyRootH6}
                          style={{color: 'blue'}}
                        >
                          {cityAirQuality.data[0].pollen_level_weed}
                        </Box>
                        <Box
                          component="span"
                          fontSize=".875rem"
                        >
                          Pollen Level Weed
                        </Box>
                      </Box>
                      <Box textAlign="center" padding=".875rem">
                        <Box
                          component="span"
                          fontSize="1.1rem"
                          fontWeight="700"
                          display="block"
                          letterSpacing=".025em"
                          className={classesCity.typographyRootH6}
                          style={{color: 'blue'}}
                        >
                          {cityAirQuality.data[0].pollen_level_grass}
                        </Box>
                        <Box
                          component="span"
                          fontSize=".875rem"
                        >
                          Pollen Level Grass
                        </Box>
                      </Box>
                      <Box textAlign="center" padding=".875rem">
                        <Box
                          component="span"
                          fontSize="1.1rem"
                          fontWeight="700"
                          display="block"
                          letterSpacing=".025em"
                          className={classesCity.typographyRootH6}
                          style={{color: 'blue'}}
                        >
                          {cityAirQuality.data[0].predominant_pollen_type}
                        </Box>
                        <Box
                          component="span"
                          fontSize=".875rem"
                        >
                          Predominant Pollen Type
                        </Box>
                      </Box>
                    </Box>
                  </Grid>
                </Grid>
              </Box>
            </Card>
          : '' }
      </Container>
    </>
  );
}

export default SearchAirqualityView;