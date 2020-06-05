import React, { Component } from 'react';
import ReactApexChart from 'react-apexcharts';

class Apexarea extends Component {

    constructor(props) {
        super(props);

        this.getTicketAmountsFromLast7Days = this.getTicketAmountsFromLast7Days.bind(this);

        this.state = {
            options: {
                chart: {
                    type: 'area',   
                    foreColor: '#9f9ea4',
                    toolbar: {
                        show: false,
                    }
                },
                dataLabels: {
                    enabled: false
                },
                stroke: {
                    curve: 'smooth',
                    width: 2
                },
                colors: ['#4090cb'],
                xaxis: {
                    categories: this.getLast7Days(),
                },
                grid: {
                    yaxis: {
                        lines: {
                            show: false,
                        }
                    }
                },
            },

            series : [{
                name: '',
                data: []
            }],
            
            tickets: []
        }
    }

    componentDidMount() {
        fetch("https://vy-automatic-ticketing-system.web.app/api/tickets").then(res => res.json()).then(
            (result) => {
                this.setState({
                    tickets: result,
                    series: [{
                        name: "Travels",
                        data: this.getTicketAmountsFromLast7Days(result.tickets)
                    }]
                });
            },
            (error) => {this.setState({error})}
        )
    }

    getTicketAmountsFromLast7Days = (tickets) => {
        const lastWeek = this.getLast7Days();
        const amounts = [];

        lastWeek.forEach((day, index) => {
            const amount = tickets.filter(ticket => (ticket.boarded).includes(day)).length;
            amounts.push(amount);
        })

        return amounts
    }

    formatDate(date) {
        let dd = date.getDate();
        let mm = date.getMonth() + 1;
        const yyyy = date.getFullYear();
        if (dd < 10) {dd = '' + dd};
        if (mm < 10) {mm = '' + mm}
        date = mm + "-" + dd;

        return date
    }

    getLast7Days() {
        const dates = [];

        for (let i = 0; i < 7; i++) {
            const date = new Date();
            date.setDate(date.getDate() - i);
            dates.push(this.formatDate(date));
        }

        return dates.reverse()
    }

    render() {
        return (
            <React.Fragment>
                <ReactApexChart options={this.state.options} series={this.state.series} type="area" width="100%" height="299" />
            </React.Fragment>
        );
    }
}

export default Apexarea;   