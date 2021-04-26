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

// core components
import Header from "components/Headers/Header.js";

import componentStyles from "assets/theme/views/admin/icons.js";

const useStyles = makeStyles(componentStyles);

const Icons = () => {
  const classes = useStyles();
  const theme = useTheme();
  const [copiedText, setCopiedText] = useState();
  const [errorMessage, setErrorMessage] = useState('');

  const [pagination, setPagination] = useState({
    data: [],
    offset: 0,
    numberPerPage: 12,
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
      console.log(res);
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
      <Header />
      { errorMessage ? errorMessage : (
        <Container
          maxWidth={false}
          component={Box}
          marginTop="-6rem"
          classes={{ root: classes.containerRoot }}
        >
          {/* Table */}
          <Grid container component={Box} marginBottom="39px">
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
                      >
                        <Card style={{padding: 40, textAlign: 'center'}}>
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
          </Grid>

        </Container>
      )}
    </>
  );
};

export default Icons;
