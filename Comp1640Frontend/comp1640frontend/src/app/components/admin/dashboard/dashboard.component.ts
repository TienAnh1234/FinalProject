import { Component, OnInit } from '@angular/core';
import { ChartData, ChartConfiguration } from 'chart.js';
import { PopularSubject } from 'src/app/common/popularsubject';
import { DashboardService } from 'src/app/services/dashboard.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  top3Major: ChartData<'bar', number[], string> = { labels: [], datasets: [] };
  bottom3Major: ChartData<'bar', number[], string> = { labels: [], datasets: [] };

  tutorsMajorChartData: ChartData<'doughnut', number[], string> = { labels: [], datasets: [] };
  newAccountsChartData: ChartData<'bar', number[], string> = { labels: [], datasets: [] };
  newBlogsChartData: ChartData<'line', number[], string> = { labels: [], datasets: [] };
  newpaymentChartData: ChartData<'line', number[], string> = { labels: [], datasets: [] };

  totalPaymentsAmount: number = 0;


  totalAccounts: number = 0;
  totalBlogs: number = 0;




  pieChartOptions: ChartConfiguration<'doughnut'>['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      tooltip: {
        callbacks: {
          label: (tooltipItem: any) => {
            const label = tooltipItem.label || '';
            const value = tooltipItem.raw;
            return `${label}: ${value} people`;
          }
        }
      }
    }
  };

  barChartOptions: ChartConfiguration<'bar'>['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    scales: {
      x: {
        ticks: { autoSkip: false },
        grid: { display: true }
      },
      y: {
        beginAtZero: true,
        ticks: { stepSize: 1 },
        grid: { display: true }
      }
    },
    plugins: {  
      tooltip: {
        mode: 'index',
        callbacks: {
          label: (tooltipItem: any) => {
            return `${tooltipItem.label}: ${tooltipItem.raw} People`;
          }
        }
      }
    }
  };

  lineChartOptions: ChartConfiguration<'line'>['options'] = {
    responsive: true,
    elements: { line: { tension: 0.4 } },
    plugins: {
      tooltip: { enabled: true },
      legend: { display: true }
    },
    scales: {
      x: { display: true },
      y: { beginAtZero: true }
    }
  };


  constructor(private dashboardService: DashboardService) { }

  ngOnInit(): void {
    this.fetchChartData();
  }

  fetchChartData(): void {
    this.dashboardService.getPopularSubjectProjection().subscribe((data: PopularSubject[]) => {
      const top3 = data.slice(0, 3);
      const labels = top3.map(item => `${item.majorName} - ${item.gradeName}`);
      const values = top3.map(item => item.tutorCount);
      console.log(data)
      
      // this.studentsMajorChartData = {
      //   labels,
      //   datasets: [{
      //     data:  values,
      //     backgroundColor: ['#42A5F5', '#64B5F6', '#90CAF9', '#1976D2']
      //   }]
      // }; 

      this.top3Major = {
        labels,
        datasets: [
          {
            data: [values[0], values[1], values[2]],
            label: 'Number of tutor',
            backgroundColor: '#FFAB91',
            borderColor: '#388E3C',
            borderWidth: 1
          }
        ]
      }; 

    });

    this.dashboardService.getPopularSubjectProjection().subscribe((data: PopularSubject[]) => {
      const bottom3 = data.slice(-3);
      const labels = bottom3.map(item => `${item.majorName} - ${item.gradeName}`);
      const values = bottom3.map(item => item.tutorCount);
      console.log(data)
      
      // this.studentsMajorChartData = {
      //   labels,
      //   datasets: [{
      //     data:  values,
      //     backgroundColor: ['#42A5F5', '#64B5F6', '#90CAF9', '#1976D2']
      //   }]
      // }; 

      this.bottom3Major = {
        labels,
        datasets: [
          {
            data: [values[0], values[1], values[2]],
            label: 'Number of tutor',
            backgroundColor: '#FFAB91',
            borderColor: '#388E3C',
            borderWidth: 1
          }
        ]
      }; 

    });

    // this.dashboardService.getTutorsPerMajor().subscribe(data => {
    //   const labels = data.map((item: any) => item.major);
    //   const values = data.map((item: any) => item.count);
    //   console.log(data)

    //   this.tutorsMajorChartData = {
    //     labels,
    //     datasets: [{
    //       data: values,
    //       label: 'Tutors',
    //       backgroundColor: ['#FF7043', '#FF8A65', '#FFAB91', '#D84315']
    //     }]
    //   };
    // });

    this.dashboardService.getTotalAccounts().subscribe(data => {
      this.totalAccounts = data.student + data.tutor;

      this.newAccountsChartData = {
        labels: ['Student', 'Tutor'],
        datasets: [
          {
            data: [data.student, 0],
            label: 'Student Account',
            backgroundColor: '#66BB6A',
            borderColor: '#388E3C',
            borderWidth: 1
          },
          {
            data: [0, data.tutor],
            label: 'Tutor Account',
            backgroundColor: '#EF5350',
            borderColor: '#C62828',
            borderWidth: 1
          }
        ]
      };      
    });

    this.dashboardService.getBlogsByMonth().subscribe(response => {
      const englishMonths = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
      const labels = englishMonths;
      console.log(response)
      
      const data = Array(12).fill(0);
      response.forEach((item: any) => {
        const month = item.month;
        const count = item.count;
        data[month - 1] = count;
      }); 

    this.totalBlogs = data.reduce((a, b) => a + b, 0);

    this.newBlogsChartData = {
        labels,
        datasets: [{
          data,
          label: `Blogs by Month`,
          borderColor: '#8E24AA',
          backgroundColor: 'rgba(186,104,200,0.2)',
          fill: true,
          tension: 0.3
        }]
      };
    });


    this.dashboardService.getMonthlyTotalAmount().subscribe(response => {
      const englishMonths = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
      const labels = englishMonths;
      console.log(response.length)

      const data = Array(12).fill(0);
      response.forEach((item: any) => {
        const month = item.month;
        const totalAmount = item.totalAmount / 100;

        data[month - 1] = totalAmount;
      }); 

      console.log(data)


    this.totalPaymentsAmount= data.reduce((a, b) => a + b, 0);

    this.newpaymentChartData = {
        labels,
        datasets: [{
          data,
          label: `Amount by Month(VND)`,
          borderColor: '#8E24AA',
          backgroundColor: 'rgba(186,104,200,0.2)',
          fill: true,
          tension: 0.3
        }]
      };
    });




  }

}
