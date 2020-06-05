import React, { Component } from 'react';
import { Row, Col, Card, CardBody } from 'reactstrap';
import { Map, InfoWindow, Marker, GoogleApiWrapper } from 'google-maps-react';
import { Link } from 'react-router-dom';
import { activateAuthLayout } from '../../../store/actions';
import { connect } from 'react-redux';
import Settingmenu from '../Subpages/Settingmenu';
import LightData from './LightData';

const LoadingContainer = (props) => (
    <div>Loading...</div>
)

class Mapsgoogle extends Component {

    constructor(props) {
        super(props);
        this.onMarkerClick = this.onMarkerClick.bind(this);
        this.state = {
            showingInfoWindow: false,
            activeMarker: {},
            selectedPlace: {},
            beacons: []
        };
    }

    componentDidMount() {
        this.props.activateAuthLayout();

        fetch("https://vy-automatic-ticketing-system.web.app/api/beacons").then(res => res.json()).then(
            (result) => {this.setState({beacons: result.filter(beacon => beacon.type === "station")})},
            (error) => {this.setState({error})}
        )
    }

    onMarkerClick(props, marker, e) {
        alert('You clicked in this marker');
    }

    render() {
        const { error, beacons } = this.state;

        return (
            <React.Fragment>
                <div className="content">
                    <div className="container-fluid">
                        <div className="page-title-box">
                            <Row className="align-items-center">
                                <Col sm="6">
                                    <h4 className="page-title">Beacon Map</h4>
                                    <ol className="breadcrumb">
                                        <li className="breadcrumb-item"><Link to="#"><i className="mdi mdi-home-outline"></i></Link></li>
                                        <li className="breadcrumb-item active">Beacon Map</li>
                                    </ol>
                                </Col>
                                <Col sm="6">
                                    <div className="float-right d-none d-md-block">
                                        <Settingmenu />
                                    </div>
                                </Col>
                            </Row>
                        </div>
                        <Row>
                            <Col lg="12">
                                <Card>
                                    <CardBody>
                                        <h4 className="mt-0 header-title">Beacon Locations</h4>
                                        <p className="text-muted mb-4">An overview of the beacons.</p>
                                        <div id="gmaps-overlay" className="gmaps">
                                            <Map google={this.props.google}
                                                style={{ height: 550, width: 1580, position: 'relative' }}
                                                zoom={5}
                                                initialCenter={{
                                                    lat: 63.4305,
                                                    lng: 10.3951
                                                }}>
                
                                                {beacons.map(beacon => {
                                                    return (
                                                        <Marker
                                                            key={beacon.uuid}
                                                            title={beacon.station}
                                                            name={beacon.station}
                                                            position={{ lat: beacon.geolocation._latitude, lng: beacon.geolocation._longitude }} 
                                                        />
                                                    )
                                                })}
                                            </Map>
                                        </div>
                                    </CardBody>
                                </Card>
                            </Col>
                        </Row>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default connect(null, { activateAuthLayout })(GoogleApiWrapper({
    apiKey: "AIzaSyAbvyBxmMbFhrzP9Z8moyYr6dCr-pzjhBE",
    LoadingContainer: LoadingContainer,
    v: "3"
})(Mapsgoogle));



