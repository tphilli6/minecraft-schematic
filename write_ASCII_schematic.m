clc
clear all
close all


% data=importdata('byte_output');
% data=importdata('MC_mound_and_void.txt');

% data=importdata('test.txt');
% lz=data(1);%w 
% ly=data(2);%h
% lx=data(3);%l

% load('MC.mat');
% load('MC_mound_and_void.mat')
% load('MC_negative.mat'); Im_MC=MC_void;
load('MC_negative_connected_cleaned.mat'); Im_void=Im_MC;
Ivoids=find(Im_void==1);
load('MC_mound.mat'); Im_mound=MC_mound;

Im_MC=Im_mound;
Im_MC(Ivoids)=0;


fid=fopen('voids.txt','w');
lx=size(Im_MC,2);
lz=size(Im_MC,1);
ly=size(Im_MC,3);
fprintf(fid,'%3.0f\n',lz);
fprintf(fid,'%3.0f\n',ly);
fprintf(fid,'%3.0f\n',lx);
cnt=1;
for k=ly:-1:1
% for k=1:ly
    for j=1:lx
        for i=1:lz
            fprintf(fid,'%1.0f\n',Im_MC(i,j,k));
            D(cnt)=Im_MC(i,j,k);
            cnt=cnt+1;
        end
    end
end


fclose(fid);

%xzy

% D=reshape(Im_MC,[lx*lz*ly,1]);
% Mat=Im_MC;


% D=data(4:end);
% Mat=reshape(D,[lx,lz,ly]);

% xx=1:lx;
% yy=1:ly;
% zz=1:lz;
% [x,y,z]=meshgrid(xx,zz,yy);
% 
% I=find(Mat>0);




% I=find(D==3); D(I)=2;
% I=find(D==4); D(I)=1;
% I=find(D==17); D(I)=5;
% I=find(D==43 | D==44); D(I)=1;
% I=find(D==50); D(I)=0;
% I=find(D==193); D(I)=0;
% 
% bids=unique(D);
% c='krgb';

% for i=2:numel(bids)
%     I=find(D==bids(i));
%     plot3(x(I),y(I),z(I),[c(i-1),'s'],'MarkerFaceColor',c(i-1),'MarkerSize',5)
%     hold on
% end
% axis equal
%         end
%     end
% endaxis equal

% lx=32;
% ly=32;
% lz=32;

% D=zeros(lx,lz,ly);
% D(8:24,8:24,8:24)=1;
% Dvec=reshape(D,[lx*ly*lz,1]);
fid=fopen('byte_input_mound','w')

fprintf(fid,'%3.0f\n',[lz;ly;lx]);
fprintf(fid,'%1.0f\n',D);
fclose(fid);

