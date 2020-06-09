import React, { Component } from 'react';
import { Row, Col, Card, CardBody } from 'reactstrap';
import { activateAuthLayout } from '../../../store/actions';
import { withRouter } from 'react-router-dom';
import { connect } from 'react-redux';
import Settingmenu from '../Subpages/Settingmenu';
import { Link } from 'react-router-dom';

import Rightsidebar from '../../../components/RightSidebar';
//Charts
import Apexarea from '../../../containers/charts/apex/apexarea';
import Apexbar from '../../../containers/charts/apex/apexbar';

import DashboardRightSidebar from './rightbar';

class Dashboard extends Component {

    constructor(props) {
        super(props);
        this.state = { activeTab: '1', activeother: '1', startDate: new Date(), tickets: [], capacities: [], beacons: [] }
        this.toggleStock = this.toggleStock.bind(this);
        this.toggleMessages = this.toggleMessages.bind(this);
    }

    componentDidMount() {
        this.props.activateAuthLayout();

        fetch("https://vy-automatic-ticketing-system.web.app/api/tickets").then(res => res.json()).then(
            (result) => {this.setState({tickets: result.tickets})},
            (error) => {this.setState({error})}
        )

        fetch("https://vy-automatic-ticketing-system.web.app/api/capacity").then(res => res.json()).then(
            (result) => {this.setState({capacities: result})},
            (error) => {this.setState({error})}
        )

        fetch("https://vy-automatic-ticketing-system.web.app/api/beacons").then(res => res.json()).then(
            (result) => {this.setState({beacons: result})},
            (error) => {this.setState({error})}
        )
    }

    toggleStock(tab) {
        if (this.state.activeTab !== tab) {
            this.setState({
                activeTab: tab
            });
        }
    }

    toggleMessages(tab) {
        if (this.state.activeother !== tab) {
            this.setState({
                activeother: tab
            });
        }
    }
    
    render() {

        return (
            <React.Fragment>
                <div className="content dasboard-content">
                    <div className="container-fluid">
                        <div className="page-title-box">
                            <div className="row align-items-center">
                                <div className="col-sm-6">
                                    <h4 className="page-title">Dashboard</h4>
                                    <ol className="breadcrumb">
                                        <li className="breadcrumb-item active">Welcome to Vy Dashboard</li>
                                    </ol>
                                </div>
                                <div className="col-sm-6">
                                    <div className="float-right d-none d-md-block">
                                        <Settingmenu />
                                    </div>
                                </div>
                            </div>
                        </div>

                        <Row>
                            <Col lg="4">
                                <Card className="mini-stat bg-pattern">
                                    <CardBody className="mini-stat-img">
                                        <div className="mini-stat-icon">
                                            <i className="dripicons-view-list bg-soft-primary text-primary float-right h4"></i>
                                        </div>
                                        <h6 className="text-uppercase mb-3 mt-0">Total Travels</h6>
                                        <h5 className="mb-3">{(this.state.tickets).length}</h5>
                                    </CardBody>
                                </Card>
                            </Col>
                            <Col lg="4">
                                <Card className="mini-stat bg-pattern">
                                    <CardBody className="mini-stat-img">
                                        <div className="mini-stat-icon">
                                            <i className="ion ion-md-train bg-soft-primary text-primary float-right h4"></i>
                                        </div>
                                        <h6 className="text-uppercase mb-3 mt-0">Total Trains</h6>
                                        <h5 className="mb-3">{(this.state.capacities).length}</h5>
                                    </CardBody>
                                </Card>
                            </Col>
                            <Col lg="4">
                                <Card className="mini-stat bg-pattern">
                                    <CardBody className="mini-stat-img">
                                        <div className="mini-stat-icon">
                                            <i className="ion ion-md-bluetooth bg-soft-primary text-primary float-right h4"></i>
                                        </div>
                                        <h6 className="text-uppercase mb-3 mt-0">Total Beacons</h6>
                                        <h5 className="mb-3">{(this.state.beacons).length}</h5>
                                    </CardBody>
                                </Card>
                            </Col>
                        </Row>

                        <Row>
                            <Col xl="4">
                                <Card>
                                    <CardBody>
                                        <h4 className="mt-0 header-title mb-4">Travels Last 7 Days</h4>
                                        <div id="area-chart">
                                            <Apexarea />
                                        </div>
                                    </CardBody>
                                </Card>
                            </Col>

                            <Col xl="8">
                                <Card>
                                    <CardBody>
                                        <h4 className="mt-0 header-title mb-4">Travels This Year</h4>
                                        <div id="column-chart" dir="ltr">
                                            <Apexbar />
                                        </div>
                                    </CardBody>
                                </Card>
                            </Col>
                        </Row>
                    </div>
                </div>

                <Rightsidebar>
                    <DashboardRightSidebar />
                </Rightsidebar>
            </React.Fragment>
        );
    }
}

export default withRouter(connect(null, { activateAuthLayout })(Dashboard));


