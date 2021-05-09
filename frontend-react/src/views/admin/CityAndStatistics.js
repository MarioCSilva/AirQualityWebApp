import React, { useState, useEffect } from "react";
// react component that copies the given text inside your clipboard
// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
import { useTheme } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import CardHeader from "@material-ui/core/CardHeader";
import Container from "@material-ui/core/Container";
import Grid from "@material-ui/core/Grid";
import ReactPaginate from 'react-paginate';
import "../../assets/scss/App.css";
import AirQualityService from "../../services/AirQualityService.js";
import Typography from "@material-ui/core/Typography";
import Modal, {closeStyle} from 'simple-react-modal'
import componentStylesCity from "assets/theme/views/admin/profile.js";
import LocationOn from "@material-ui/icons/LocationOn";

// core components
import Header from "components/Headers/Header.js";

import componentStyles from "assets/theme/views/admin/icons.js";

const useStyles = makeStyles(componentStyles);
const useStylesCity = makeStyles(componentStylesCity);

const CityAndStatistics = () => {
  const classes = useStyles();
  const theme = useTheme();
  const [errorMessage, setErrorMessage] = useState('');
  const [showx, setShow] = useState(false);
  const [cityAirQuality, setCityAirQuality] = useState(null);
  const [counter, setCounter] = useState(0);

  const classesCity = useStylesCity();

  const show = (cityId) => {
    AirQualityService.getCityAirQualityById(cityId).then((res) => {
      setCityAirQuality(res);
      setErrorMessage(null);
    }).catch((err) => {
      setErrorMessage("Rest API Unavailable");
    })
    setCounter(counter+1)
    setShow(true)
  }
 
  const close = () => {
    setCityAirQuality(null);
    setShow(false);
  }

  const [pagination, setPagination] = useState({
    data: [],
    offset: 0,
    numberPerPage: 9,
    pageCount: 0,
    currentData: []
  });

  useEffect(() => {
    AirQualityService.getAllCities().then((res) => {
      setErrorMessage(null);
      setPagination((prevState) => ({
        ...prevState,
        data: res,
      }));
    }).catch((err) => {
      console.log(err)
      setErrorMessage("Couldn't fetch cities");
    })
  }, [])

  useEffect(() => {
    setPagination((prevState) => ({
      ...prevState,
      pageCount: prevState.data.length / prevState.numberPerPage,
      currentData: prevState.data.slice(pagination.offset, pagination.offset + pagination.numberPerPage)
    }))
  }, [pagination.numberPerPage, pagination.offset, pagination.data])

  const handlePageClick = event => {
    const selected = event.selected;
    const offset = selected * pagination.numberPerPage
    setPagination({ ...pagination, offset })
  }

  return (
    <>

      <Header update={counter} />

      <Modal
      containerStyle={{minWidth: 700}}
      closeOnOuterClick={true}
      show={showx}
      onClose={close.bind(this)}>
 
      <a style={closeStyle} onClick={close.bind(this)}>X</a>


      { cityAirQuality ? 
            <Card classes={{ root: classesCity.cardRoot }} style={{marginTop:0}}>
              <Box component={Grid} container justifyContent="center" style={{paddingTop:20}}>
              
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

      </Modal>
 
        <Container
          maxWidth={false}
          component={Box}
          marginTop="-6rem"
          classes={{ root: classes.containerRoot }}
          >
          {/* Table */}
          <Grid container component={Box} marginBottom="39px">
            { errorMessage ? <span style={{color: 'white', fontSize: 18}}>{errorMessage}</span> : (
            <Grid item xs={12}>
              <Card classes={{ root: classes.cardRoot }}>
                <CardHeader
                  className={classes.cardHeader}
                  title="Portugal Cities"
                  titleTypographyProps={{
                    component: Box,
                    marginBottom: "0!important",
                    variant: "h3",
                  }}
                ></CardHeader>
                <CardContent>
                  <Grid container>
                    {pagination.currentData && pagination.currentData.map(((item, index) => (
                      <>
                      <Grid
                        key={item.city_id}
                        item
                        lg={4}
                        md={12}
                        xs={12}
                        component={Box}
                        paddingLeft="15px"
                        paddingRight="15px"
                        paddingBottom="15px"
                        onClick={() => show(item.city_id)}
                        style={{'cursor': 'pointer', height: "100%"}}
                      >
                        <Card style={{padding: 40, textAlign: 'center', height: '100%'}}>
                        <Typography variant="h1" style={{color: 'blue'}}>
                          {item.city_name}, {item.country_code}
                        </Typography>
                        <Typography variant="h3" style={{paddingRight: 10, color: 'light-blue'}}>
                          <h3>State Code: {item.state_code}</h3>
                        </Typography>
                        <Typography variant="h3" style={{paddingRight: 10, color: 'light-blue'}}>
                          Latitude: {item.lat}
                        </Typography>
                        <Typography variant="h3" style={{paddingRight: 10, color: 'light-blue', marginTop: 0}}>
                          Longitude: {item.lon}
                        </Typography>
                        </Card>
                      </Grid>
                      </>
                    )))
                    }
                  </Grid>
                    <ReactPaginate
                      previousLabel={'previous'}
                      nextLabel={'next'}
                      breakLabel={'...'}
                      pageCount={pagination.pageCount}
                      marginPagesDisplayed={2}
                      pageRangeDisplayed={5}
                      onPageChange={handlePageClick}
                      containerClassName={'pagination'}
                      activeClassName={'active'}
                    />
                </CardContent>
              </Card>
            </Grid>
          )}
          </Grid>

        </Container>
    </>
  );
};

export default CityAndStatistics;
