import React, { Component } from 'react';
import { Row, Col, Card, CardBody } from 'reactstrap';
import { MDBDataTable } from 'mdbreact';
import { activateAuthLayout } from '../../../store/actions';
import { connect } from 'react-redux';
import Settingmenu from '../Subpages/Settingmenu';
import { Link } from 'react-router-dom';

class Tabledatatable extends Component {

    constructor(props) {
        super(props);
        this.state = {
            error: null,
            tickets: []
        };
    }

    componentDidMount() {
        this.props.activateAuthLayout();

        fetch("https://vy-automatic-ticketing-system.web.app/api/tickets").then(res => res.json()).then(
            (result) => {this.setState({tickets: result.tickets})},
            (error) => {this.setState({error})}
        )
    }

    render() {
        const { error, tickets } = this.state;

        const data = {
            columns: [
                {
                    label: 'Email',
                    field: 'email',
                    sort: 'asc',
                    width: 150
                },
                {
                    label: 'Start',
                    field: 'startingStation',
                    sort: 'asc',
                    width: 270
                },
                {
                    label: 'Stop',
                    field: 'endingStation',
                    sort: 'asc',
                    width: 200
                },
                {
                    label: 'Travel Time',
                    field: 'timeTaken',
                    sort: 'asc',
                    width: 100
                },
                {
                    label: 'Boarded',
                    field: 'boarded',
                    sort: 'asc',
                    width: 150
                },
                {
                    label: 'Disembarked',
                    field: 'disembarked',
                    sort: 'asc',
                    width: 100
                },
                {
                    label: 'Price',
                    field: 'price',
                    sort: 'asc',
                    width: 100
                }
            ],
            rows: tickets
        };


        return (
            <React.Fragment>
                <div className="content">
                    <div className="container-fluid">
                        <div className="page-title-box">
                            <Row className="align-items-center">
                                <Col sm="6">
                                    <h4 className="page-title">Customer Travels</h4>
                                    <ol className="breadcrumb">
                                        <li className="breadcrumb-item"><Link to="#"><i className="mdi mdi-home-outline"></i></Link></li>
                                        <li className="breadcrumb-item active">Customer Travels</li>
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
                            <Col xl="3" md="6">
                                <Card className="bg-pattern">
                                    <CardBody>
                                        <div className="float-right">
                                            <i className="dripicons-view-list text-primary h4 ml-3"></i>
                                        </div>
                                        <h5 className="font-20 mt-0 pt-1">{tickets.length}</h5>
                                        <p className="text-muted mb-0">Total Travels</p>
                                    </CardBody>
                                </Card>
                            </Col>
                            <Col xl="3" md="6">
                                <Card className="bg-pattern">
                                    <CardBody>
                                        <div className="float-right">
                                            <i className="dripicons-checkmark text-primary h4 ml-3"></i>
                                        </div>
                                        <h5 className="font-20 mt-0 pt-1">{(tickets.filter(ticket => ticket.endingStation !== "N/A")).length}</h5>
                                        <p className="text-muted mb-0">Completed Travels</p>
                                    </CardBody>
                                </Card>
                            </Col>
                            <Col xl="3" md="6">
                                <Card className="bg-pattern">
                                    <CardBody>
                                        <div className="float-right">
                                            <i className="dripicons-hourglass text-primary h4 ml-3"></i>
                                        </div>
                                        <h5 className="font-20 mt-0 pt-1">{(tickets.filter(ticket => ticket.endingStation === "N/A")).length}</h5>
                                        <p className="text-muted mb-0">Travels In Progress</p>
                                    </CardBody>
                                </Card>
                            </Col>
                        </Row>

                        <Row>
                            <Col>
                                <Card>
                                    <CardBody>
                                        <MDBDataTable
                                            bordered
                                            data={data}
                                        />
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

export default connect(null, { activateAuthLayout })(Tabledatatable);


